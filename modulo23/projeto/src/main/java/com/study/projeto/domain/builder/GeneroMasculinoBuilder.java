package com.study.projeto.domain.builder;

import com.study.projeto.domain.model.Pessoa;
import com.study.projeto.enums.Genero;

public class GeneroMasculinoBuilder implements PessoaBuilder {

  private Pessoa pessoa;

  @Override
  public void reset() {
    this.pessoa = new Pessoa();
    this.pessoa.setGenero(Genero.MASCULINO);
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
