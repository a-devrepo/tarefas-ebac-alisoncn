package com.study.projeto.domain.model;

import com.study.projeto.anotacoes.Codigo;
import com.study.projeto.enums.Genero;

public class Pessoa {
  @Codigo private long id;
  private String nome;
  private String sobreNome;
  private int idade;
  private Genero genero;

  public Pessoa() {}

  public Pessoa(long id, String nome, String sobreNome, int idade, Genero genero) {
    this.id = id;
    this.nome = nome;
    this.sobreNome = sobreNome;
    this.idade = idade;
    this.genero = genero;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSobreNome() {
    return sobreNome;
  }

  public void setSobreNome(String sobreNome) {
    this.sobreNome = sobreNome;
  }

  public int getIdade() {
    return idade;
  }

  public void setIdade(int idade) {
    this.idade = idade;
  }

  public Genero getGenero() {
    return genero;
  }

  public void setGenero(Genero genero) {
    this.genero = genero;
  }

  @Override
  public String toString() {
    return "Pessoa{"
        + "nome='"
        + nome
        + '\''
        + ", sobreNome='"
        + sobreNome
        + '\''
        + ", idade="
        + idade
        + ", genero="
        + genero
        + '}';
  }
}
