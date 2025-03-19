package br.com.fiap.Projetobankcg.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.Projetobankcg.model.Conta;
import br.com.fiap.Projetobankcg.model.Pix;
import br.com.fiap.Projetobankcg.model.TipoConta;
import br.com.fiap.Projetobankcg.model.Transacao;

@RestController
public class ContaController {

    private List<Conta> dados = new ArrayList<>();

    // Metodo Get Home
    @GetMapping("/")
    public String home() {
        return "Caike Dametto RM:558614 \nGuilherme Janunzzi RM:558461";
    }

    // Metodo GET
    @GetMapping("/contas")
    public List<Conta> index() {
        return dados;
    }

    // Metodo POST
    @PostMapping("/contas")
    public ResponseEntity<Conta> create(@RequestBody Conta conta) {
        // Validando se o nome do titular está vazio
        if (conta.getNomeTitular() == null || conta.getNomeTitular().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do titular é obrigatório!");
        }

        // Validando se o CPF do titular está vazio
        if (conta.getCpf() == null || conta.getCpf().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do titular é obrigatório!");
        }

        // Validando se a data de abertura está no futuro
        if (conta.getDataDeAbertura() != null && conta.getDataDeAbertura().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de abertura não pode ser no futuro!");
        }

        // Validando se o saldo inicial é negativo
        if (conta.getSaldoInicial() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O saldo inicial não pode ser negativo!");
        }

        // Validando se o tipo de conta é valido
        if (conta.getTipoConta() == null || !isTipoContaValido(conta.getTipoConta())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tipo de conta inválido. Deve ser 'Corrente', 'Poupança' ou 'Salario'!");
        }

        // Adicionando dados depois de validados
        dados.add(conta);
        return ResponseEntity.status(201).body(conta);
    }

    // Metodo GET por ID
    @GetMapping("/contas/{id}")
    public Conta getById(@PathVariable Long id) {
        return getConta(id);
    }

    // Metodo GET por ID
    @GetMapping("/contas/cpf/{cpf}")
    public Conta getByCPF(@PathVariable String cpf) {
        return dados
                .stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Metodo PUT
    @PutMapping("/contas/{id}")
    public Conta update(@PathVariable Long id, @RequestBody Conta conta) {

        dados.remove(getConta(id));
        conta.setId(id);
        dados.add(conta);

        return conta;
    }

    // Metódo DELETE
    @DeleteMapping("/contas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        dados.remove(getConta(id));
    }

    // Método para depositar
    @PostMapping("/contas/depositar")
    public ResponseEntity<Conta> depositar(@RequestBody Transacao transacao) {
        // Validando sw o valor do deposito é válido
        if (transacao.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O valor da transação não pode ser zero ou negativo!");
        }

        // Buscando conta pelo ID no sistema
        Conta conta = getConta(transacao.getIdConta());

        // Adicionando o saldo na conta
        conta.setSaldoInicial(conta.getSaldoInicial() + transacao.getValor());

        return ResponseEntity.ok(conta);
    }

    // Metodo para sacar
    @PostMapping("/contas/sacar")
    public ResponseEntity<Conta> sacar(@RequestBody Transacao transacao) {
        // Validando se o valor do saque é válido
        if (transacao.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O valor da transação não pode ser zero ou negativo!");
        }

        // Buscando conta pelo ID no sistema
        Conta conta = getConta(transacao.getIdConta());

        // Verificando se há saldo sulficiente para saque na conta
        if (conta.getSaldoInicial() < transacao.getValor()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insulficiente!");
        }

        // Removendo o valor sacado da conta
        conta.setSaldoInicial(conta.getSaldoInicial() - transacao.getValor());

        return ResponseEntity.ok(conta);
    }

    // Metodo PIX
    @PostMapping("/contas/pix")
    public ResponseEntity<Conta> realizarPix(@RequestBody Pix pix) {
        // Verificando se o valor do PIX é valido
        if (pix.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do PIX não pode ser zero ou negativo!");
        }

        // Buscando a conta no sistema
        Conta contaOrigem = getConta(pix.getIdContaOrigem());
        Conta contaDestino = getConta(pix.getIdContaDestino());

        // Verificando se a conta origem possui saldo sulficiente para realizar o PIX
        if (contaOrigem.getSaldoInicial() < pix.getValor()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insulficiente!");
        }

        // Removendo o valor da Conta de Origem e adicionando na Conta de Destino
        contaOrigem.setSaldoInicial(contaOrigem.getSaldoInicial() - pix.getValor());
        contaDestino.setSaldoInicial(contaDestino.getSaldoInicial() + pix.getValor());

        return ResponseEntity.ok(contaOrigem);
    }

    // Função para buscar contas no banco por ID
    private Conta getConta(Long id) {
        return dados
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    // Função para validar se o tipo de conta é valido
    private boolean isTipoContaValido(TipoConta tipoConta) {
        return tipoConta == TipoConta.Corrente || tipoConta == TipoConta.Poupanca || tipoConta == TipoConta.Salario;
    }
}
