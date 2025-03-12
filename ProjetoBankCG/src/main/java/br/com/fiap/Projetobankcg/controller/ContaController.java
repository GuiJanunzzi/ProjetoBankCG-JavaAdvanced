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

    //Metodo Get
    @GetMapping("/contas")
    public List<Conta> index(){
        return dados;
    }

    //Metodo Post
    @PostMapping("/contas")
    public ResponseEntity<Conta> create(@RequestBody Conta conta){
        dados.add(conta);
        return ResponseEntity.status(201).body(conta);
    }

    //Metodo Get por Conta
    @GetMapping("/contas/{numero}")
    public Conta get(@PathVariable Long numero){
        return getConta(numero);
    }

    //Metódo Delete 
    @DeleteMapping("/contas/{numero}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long numero){
        dados.remove(getConta(numero));
    }

    //Metodo Edit
    @PutMapping("/contas/{numero}")
    public Conta update(@PathVariable Long numero, @RequestBody Conta conta){

        dados.remove(getConta(numero));
        conta.setNumero(numero);
        dados.add(conta);

        return conta;
    }
    

    private Conta getConta(Long numero) {
        return dados
        .stream()
        .filter(c -> c.getNumero().equals(numero))
        .findFirst()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

    }

    



}
