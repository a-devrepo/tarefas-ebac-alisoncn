package com.javabackend.projeto;

import java.util.Objects;

/**
 * @author Alison
 * @version 1.0
 * @since 2024-04-22
 */
public class Cliente {

    private String nome;
    private String sobrenome;
    private Integer idade;
    private String sexo;
    private String email;

    public Cliente() {
    }

    public Cliente(String nome, String sobrenome, Integer idade, String sexo, String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.sexo = sexo;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEmail() {
        return email;
    }

    /**
     *
     * @since version 1.0
     */
    public String nomeCompleto() {
        return this.nome + " " + this.sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome)
                && Objects.equals(sobrenome, cliente.sobrenome)
                && Objects.equals(idade, cliente.idade)
                && Objects.equals(sexo, cliente.sexo)
                && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sobrenome, idade, sexo, email);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", idade=" + idade +
                ", sexo='" + sexo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}