package com.study.projeto.domain.builders;

import com.study.projeto.domain.model.relational.Endereco;

public class EnderecoBuilder {

  private Endereco endereco;

  private EnderecoBuilder() {
    this.endereco = new Endereco();
  }

  public static EnderecoBuilder builder() {
    return new EnderecoBuilder();
  }

  public EnderecoBuilder logradouro(String logradouro) {
    this.endereco.setLogradouro(logradouro);
    return this;
  }

  public EnderecoBuilder numero(String numero) {
    this.endereco.setNumero(numero);
    return this;
  }

  public EnderecoBuilder cidade(String cidade) {
    this.endereco.setCidade(cidade);
    return this;
  }

  public EnderecoBuilder estado(String estado) {
    this.endereco.setEstado(estado);
    return this;
  }

  public Endereco build() {
    return this.endereco;
  }
}
