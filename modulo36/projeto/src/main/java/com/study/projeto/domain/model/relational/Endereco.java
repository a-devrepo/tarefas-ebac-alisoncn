package com.study.projeto.domain.model.relational;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 20)
    private String estado;

    public Endereco() {
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
