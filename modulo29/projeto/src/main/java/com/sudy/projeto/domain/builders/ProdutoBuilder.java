package com.sudy.projeto.domain.builders;

import com.sudy.projeto.domain.model.Produto;
import java.math.BigDecimal;

public class ProdutoBuilder {

  private Produto produto;

  private ProdutoBuilder() {
    this.produto = new Produto();
  }

  public static ProdutoBuilder builder() {
    return new ProdutoBuilder();
  }

  public ProdutoBuilder codigo(Long codigo) {
    this.produto.setCodigo(codigo);
    return this;
  }

  public ProdutoBuilder nome(String nome) {
    this.produto.setNome(nome);
    return this;
  }

  public ProdutoBuilder fabricante(String fabricante) {
    this.produto.setFabricante(fabricante);
    return this;
  }

  public ProdutoBuilder valor(BigDecimal valor) {
    this.produto.setValor(valor);
    return this;
  }

  public Produto build() {
    return this.produto;
  }
}
