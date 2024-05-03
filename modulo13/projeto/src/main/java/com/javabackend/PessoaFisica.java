package com.javabackend;

public class PessoaFisica extends Pessoa {
    private String cpf;

    public PessoaFisica(String nome, String endereco, String telefone, String cpf) {
        super(nome, endereco, telefone);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Nome: "+this.getNome() + ", Telefone: " + this.getTelefone() + ", Endereço: "
                + this.getEndereco() + ", Cpf: " + this.getCpf();
    }
}
