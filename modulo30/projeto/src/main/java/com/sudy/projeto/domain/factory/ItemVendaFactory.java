package com.sudy.projeto.domain.factory;

import com.sudy.projeto.domain.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemVendaFactory extends Factory {
  @Override
  public Persistente create(ResultSet resultSet) throws SQLException {
    Produto produto = (Produto) new ProdutoFactory().create(resultSet);
    ItemVenda itemVenda = new ItemVenda();
    itemVenda.setId(resultSet.getLong("id_item_venda"));
    itemVenda.setProduto(produto);
    itemVenda.setQuantidade(resultSet.getInt("quantidade"));
    itemVenda.setValorTotal(resultSet.getBigDecimal("valor_total"));
    return itemVenda;
  }

  public Persistente create(Produto produto, Venda venda, Integer quantidade) {
    ItemVenda itemVenda = new ItemVenda();
    itemVenda.setProduto(produto);
    itemVenda.setVenda(venda);
    itemVenda.adicionar(quantidade);
    return itemVenda;
  }
}
