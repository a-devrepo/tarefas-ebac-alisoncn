package com.sudy.projeto.domain.dao.impl;

import com.sudy.projeto.domain.builders.ProdutoBuilder;
import com.sudy.projeto.domain.dao.generic.ImplGenericDAO;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.exceptions.DataBaseException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplProdutoDAO extends ImplGenericDAO<Produto, Long> implements IProdutoDAO {

  @Override
  protected String getInsertQuery() {
    return new StringBuilder()
        .append("INSERT INTO ")
        .append(getNomeTabela())
        .append(" (nome,fabricante,valor)")
        .append(" VALUES (?,?,?)")
        .toString();
  }

  @Override
  protected String getUpdateQuery() {
    return new StringBuilder()
        .append("UPDATE ")
        .append(getNomeTabela())
        .append(" SET nome = ?,")
        .append(" fabricante = ?,")
        .append(" valor = ?")
        .append(" where codigo = ?")
        .toString();
  }

  @Override
  protected String getfindQuery() {
    return new StringBuilder()
        .append("SELECT * FROM ")
        .append(getNomeTabela())
        .append(" WHERE codigo = ?")
        .toString();
  }

  @Override
  protected String getRemoveQuery() {
    return new StringBuilder()
        .append("DELETE FROM ")
        .append(getNomeTabela())
        .append(" WHERE codigo = ?")
        .toString();
  }

  @Override
  protected void setInsertStatementValues(PreparedStatement stmt, Produto entity)
      throws DataBaseException {
    Produto cliente = convertObject(entity);
    try {
      stmt.setString(1, cliente.getNome());
      stmt.setString(2, cliente.getFabricante());
      stmt.setBigDecimal(3, cliente.getValor());
    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
  }

  @Override
  protected void setUpdateStatementValues(PreparedStatement stmt, Produto entity)
      throws DataBaseException {
    Produto cliente = convertObject(entity);
    try {
      stmt.setString(1, cliente.getNome());
      stmt.setString(2, cliente.getFabricante());
      stmt.setBigDecimal(3, cliente.getValor());
      stmt.setLong(4, cliente.getCodigo());

    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
  }

  @Override
  protected void setFindStatementValues(PreparedStatement stmt, Long code)
      throws DataBaseException {
    try {
      stmt.setLong(1, code);
    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
  }

  @Override
  protected void setRemoveStatementValues(PreparedStatement stmt, Long code)
      throws DataBaseException {
    try {
      stmt.setLong(1, code);
    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
  }

  @Override
  protected Produto convertObject(Produto entity) {
    return entity;
  }

  @Override
  public String getNomeTabela() {
    return Produto.class.getSimpleName().toLowerCase();
  }

  @Override
  protected void alterar(Produto entity, Produto target) {
    target.setNome(entity.getNome());
    target.setFabricante(entity.getFabricante());
    target.setValor(entity.getValor());

  }

  @Override
  protected Produto getObject(ResultSet resultSet) throws SQLException{
    return ProdutoBuilder
            .builder()
            .codigo(resultSet.getLong("codigo"))
            .nome(resultSet.getString("nome"))
            .fabricante(resultSet.getString("fabricante"))
            .valor(resultSet.getBigDecimal("valor")).build();
  }
}
