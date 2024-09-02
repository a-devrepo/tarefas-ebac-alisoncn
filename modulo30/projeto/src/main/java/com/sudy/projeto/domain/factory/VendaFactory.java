package com.sudy.projeto.domain.factory;

import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.domain.model.Venda;
import com.sudy.projeto.enums.StatusVenda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class VendaFactory extends Factory {
  @Override
  public Persistente create(ResultSet resultSet) throws SQLException {
    Cliente cliente = (Cliente) new ClienteFactory().create(resultSet);
    Venda venda = new Venda();
    venda.setCliente(cliente);
    venda.setId(resultSet.getLong("id_venda"));
    venda.setCodigo(resultSet.getString("codigo"));
    venda.setValorTotal(resultSet.getBigDecimal("valor_total"));
    venda.setDataVenda(resultSet.getTimestamp("data_venda").toInstant());
    venda.setStatusVenda(StatusVenda.getByName(resultSet.getString("status_venda")));
    return venda;
  }

  public Persistente create(
          String codigo, Instant dataVenda, Cliente cliente, StatusVenda statusVenda) {
    Venda venda = new Venda();
    venda.setCodigo(codigo);
    venda.setDataVenda(dataVenda);
    venda.setCliente(cliente);
    venda.setStatusVenda(statusVenda);
    return venda;
  }
}
