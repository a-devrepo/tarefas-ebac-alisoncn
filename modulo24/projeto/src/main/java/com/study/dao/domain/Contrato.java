package com.study.dao.domain;

import com.study.anotacoes.Codigo;

public class Contrato {

    @Codigo
    private Long codigo;
    private String assunto;

    public Contrato() {
    }

    public Contrato(String assunto) {
        this.assunto = assunto;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "codigo=" + codigo +
                ", assunto='" + assunto + '\'' +
                '}';
    }
}
