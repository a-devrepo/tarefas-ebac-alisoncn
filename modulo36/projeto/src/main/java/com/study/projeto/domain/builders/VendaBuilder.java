package com.study.projeto.domain.builders;

import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.domain.model.relational.Venda;

import java.time.Instant;

public class VendaBuilder {

  private Venda venda;

  private VendaBuilder() {
    this.venda = new Venda();
  }

  public static VendaBuilder builder() {
    return new VendaBuilder();
  }

  public VendaBuilder id(Long id) {
    this.venda.setId(id);
    return this;
  }

  public VendaBuilder cliente(Cliente cliente) {
    this.venda.setCliente(cliente);
    return this;
  }

  public VendaBuilder codigo(String codigo) {
    this.venda.setCodigo(codigo);
    return this;
  }

  public VendaBuilder dataVenda(Instant dataVenda) {
    this.venda.setDataVenda(dataVenda);
    return this;
  }

  public Venda build() {
    return this.venda;
  }
}
