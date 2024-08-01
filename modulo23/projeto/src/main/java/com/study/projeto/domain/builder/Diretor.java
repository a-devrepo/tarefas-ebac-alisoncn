package com.study.projeto.domain.builder;

public class Diretor {

  private PessoaBuilder builder;

  public void setBuilder(PessoaBuilder builder) {
    this.builder = builder;
  }

  public void criarPessoa(String nome, String sobrenome, int idade) {
    builder.reset();
    builder.definirNome(nome);
    builder.definirSobreNome(sobrenome);
    builder.definirIdade(idade);
  }
}
