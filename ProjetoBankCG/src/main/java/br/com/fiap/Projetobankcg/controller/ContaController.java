package br.com.fiap.Projetobankcg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

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

@RestController
public class ContaController {

    private List<Conta> dados = new ArrayList<>();

    //Metodo Get Home
    @GetMapping("/")
    public String home(){
        return "Caike Dametto RM:558614 \nGuilherme Janunzzi RM:558461";
    }

    //Metodo GET
    @GetMapping("/contas")
    public List<Conta> index(){
        return dados;
    }

    //Metodo POST
    @PostMapping("/contas")
    public ResponseEntity<Conta> create(@RequestBody Conta conta){
        dados.add(conta);
        return ResponseEntity.status(201).body(conta);
    }

    //Metodo GET por ID
    @GetMapping("/contas/{id}")
    public Conta getById(@PathVariable Long id){
        return getConta(id);
    }

    //Metodo GET por ID
    @GetMapping("/contas/cpf/{cpf}")
    public Conta getByCPF(@PathVariable String cpf){
        return dados
        .stream()
        .filter(c -> c.getCpf().equals(cpf))
        .findFirst()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    //Metodo PUT
    @PutMapping("/contas/{id}")
    public Conta update(@PathVariable Long id, @RequestBody Conta conta){

        dados.remove(getConta(id));
        conta.setId(id);
        dados.add(conta);

        return conta;
    }
    

    //Metódo DELETE 
    @DeleteMapping("/contas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        dados.remove(getConta(id));
    }

    private Conta getConta(Long id) {
        return dados
        .stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

    }

    



}
