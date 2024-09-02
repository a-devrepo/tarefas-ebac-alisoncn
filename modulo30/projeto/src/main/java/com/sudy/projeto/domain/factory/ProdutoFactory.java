package com.sudy.projeto.domain.factory;

import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.domain.model.Produto;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoFactory extends Factory {
  @Override
  public Persistente create(ResultSet resultSet) throws SQLException {
    Produto produto = new Produto();
    produto.setId(resultSet.getLong("id_produto"));
    produto.setCodigo(resultSet.getString("codigo"));
    produto.setNome(resultSet.getString("nome"));
    produto.setDescricao(resultSet.getString("descricao"));
    produto.setFabricante(resultSet.getString("fabricante"));
    produto.setValor(resultSet.getBigDecimal("valor"));
    return produto;
  }

  public Persistente create(String codigo, String nome, String descricao, String fabricante, BigDecimal valor) {
    Produto produto = new Produto();
    produto.setCodigo(codigo);
    produto.setNome(nome);
    produto.setDescricao(descricao);
    produto.setFabricante(fabricante);
    produto.setValor(valor);
    return produto;
  }
}
