package com.sudy.projeto.domain.model;

import com.sudy.projeto.anotacoes.Codigo;
import com.sudy.projeto.anotacoes.Entidade;

@Entidade("cliente")
public class Cliente {

    @Codigo("getCodigo")
    private Long codigo;

    private String cpf;

    private String nome;

    private String sobreNnome;

    private String telefone;

    private String endereco;

    public Cliente() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNnome() {
        return sobreNnome;
    }

    public void setSobreNnome(String sobreNnome) {
        this.sobreNnome = sobreNnome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigo=" + codigo +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", sobreNnome='" + sobreNnome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
