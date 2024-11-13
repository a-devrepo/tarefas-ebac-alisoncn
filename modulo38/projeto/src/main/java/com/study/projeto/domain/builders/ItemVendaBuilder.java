package com.study.projeto.domain.builders;

import com.study.projeto.domain.model.relational.ItemVenda;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.domain.model.relational.Venda;

public class ItemVendaBuilder {

  private ItemVenda itemVenda;

  private ItemVendaBuilder() {
    this.itemVenda = new ItemVenda();
  }

  public static ItemVendaBuilder builder() {
    return new ItemVendaBuilder();
  }

  public ItemVendaBuilder id(Long id) {
    this.itemVenda.setId(id);
    return this;
  }

  public ItemVendaBuilder produto(Produto produto) {
    this.itemVenda.setProduto(produto);
    return this;
  }

  public ItemVendaBuilder quantidade(Integer quantidade) {
    this.itemVenda.setQuantidade(quantidade);
    return this;
  }


  public ItemVendaBuilder venda(Venda venda) {
    this.itemVenda.setVenda(venda);
    return this;
  }

  public ItemVenda build() {
    return this.itemVenda;
  }
}
