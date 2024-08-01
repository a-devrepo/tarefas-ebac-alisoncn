package com.study.projeto.enums;

public enum Genero {
  MASCULINO("MASCULINO", "M"),
  FEMININO("FEMININO", "F"),
  OUTROS("OUTROS", "O");

  private String nome;
  private String sigla;

  private Genero(String nome, String sigla) {
    this.nome = nome;
    this.sigla = sigla;
  }

  private String getNome() {
    return this.nome;
  }

  public String getSigla() {
    return sigla;
  }

  public static Genero getBySigla(String sigla) {
    for (Genero genero : Genero.values()) {
      if (genero.getSigla().equalsIgnoreCase(sigla)) {
        return genero;
      }
    }
    return null;
  }
}
