package com.javabackend;

public class PessoaJuridica extends Pessoa {
    private String cnpj;

    public PessoaJuridica(String nome, String endereco, String telefone, String cnpj) {
        super(nome, endereco, telefone);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "Nome: "+this.getNome() + ", Telefone: " + this.getTelefone()
                + ", Endereço: " + this.getEndereco() + ", CNPJ: " + this.getCnpj();
    }
}
