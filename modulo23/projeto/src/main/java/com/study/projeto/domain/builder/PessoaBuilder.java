package com.study.projeto.domain.builder;

public interface PessoaBuilder {
  void reset();

  void definirNome(String nome);

  void definirSobreNome(String nome);

  void definirIdade(int idade);
}
