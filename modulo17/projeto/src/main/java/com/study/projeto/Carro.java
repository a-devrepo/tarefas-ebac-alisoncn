package com.study.projeto;

public abstract class Carro implements Persistente {

    private Long codigo;
    private String modelo;
    private String marca;
    private boolean automatico;

    public Carro(String modelo, String marca, boolean automatico) {
        this.modelo = modelo;
        this.marca = marca;
        this.automatico = automatico;
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    @Override
    public String toString() {
        return "codigo=" + codigo + " modelo=" + modelo + ", marca=" + marca + ", automatico=" + automatico;
    }
}
