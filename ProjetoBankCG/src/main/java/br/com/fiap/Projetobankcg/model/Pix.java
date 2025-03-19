package br.com.fiap.Projetobankcg.model;

public class Pix {
    private Long idContaOrigem;
    private Long idContaDestino;
    private double valor;

    public Pix(Long idContaOrigem, Long idContaDestino, double valor) {
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
    }

    public Long getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Long idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Long getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Long idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
