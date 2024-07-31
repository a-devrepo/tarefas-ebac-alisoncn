package com.study.domain.builder;

import com.study.enums.Genero;
import com.study.domain.Pessoa;

public class OutrosBuilder implements PessoaBuilder {

  private Pessoa pessoa;

  @Override
  public void reset() {
    this.pessoa = new Pessoa();
    this.pessoa.setGenero(Genero.OUTROS);
  }

  @Override
  public void definirNome(String nome) {
    this.pessoa.setNome(nome);
  }

  @Override
  public void definirSobreNome(String nome) {
    this.pessoa.setSobreNome(nome);
  }

  @Override
  public void definirIdade(int idade) {
    this.pessoa.setIdade(idade);
  }

  public Pessoa getPessoa() {
    Pessoa pessoa = this.pessoa;
    this.reset();
    return pessoa;
  }
}
