package br.com.fiap.Projetobankcg.model;

import java.time.LocalDate;
import java.util.Random;

public class Conta {

    private Long numero;
    private int agencia;
    private String nomeTitular;
    private String cpf;
    private LocalDate dataDeAbertura;
    private double saldoinicial;
    private boolean ativo;
    private TipoConta tipoConta;

    public Conta(Long numero, int agencia, String nomeTitular, String cpf, LocalDate dataDeAbertura,
            double saldoinicial, boolean ativo, TipoConta tipoConta) {
        this.numero = (numero == null)? Math.abs(new Random().nextLong()) : numero;
        this.agencia = agencia;
        this.nomeTitular = nomeTitular;
        this.cpf = cpf;
        this.dataDeAbertura = dataDeAbertura;
        this.saldoinicial = saldoinicial;
        this.ativo = ativo;
        this.tipoConta = tipoConta;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataDeAbertura() {
        return dataDeAbertura;
    }

    public void setDataDeAbertura(LocalDate dataDeAbertura) {
        this.dataDeAbertura = dataDeAbertura;
    }

    public double getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(double saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    

    
}


