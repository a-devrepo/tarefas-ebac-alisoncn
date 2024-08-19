package com.sudy.projeto.domain.dao.impl;

import com.sudy.projeto.domain.builders.ClienteBuilder;
import com.sudy.projeto.domain.dao.generic.ImplGenericDAO;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.exceptions.DataBaseException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplClienteDAO extends ImplGenericDAO<Cliente, Long> implements IClienteDAO {

  @Override
  protected String getInsertQuery() {
    return new StringBuilder()
        .append("INSERT INTO ")
        .append(getNomeTabela())
        .append(" (codigo,cpf,nome,sobrenome,telefone,endereco)")
        .append(" VALUES (nextval('sq_cliente'),?,?,?,?,?)")
        .toString();
  }

  @Override
  protected String getUpdateQuery() {
    return new StringBuilder()
        .append("UPDATE ")
        .append(getNomeTabela())
        .append(" SET cpf = ?,")
        .append(" nome = ?,")
        .append(" sobrenome = ?,")
        .append(" telefone = ?,")
        .append(" endereco = ?")
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
  protected void setInsertStatementValues(PreparedStatement stmt, Cliente entity)
      throws DataBaseException {
    Cliente cliente = convertObject(entity);
    try {
      stmt.setString(1, cliente.getCpf());
      stmt.setString(2, cliente.getNome());
      stmt.setString(3, cliente.getSobreNnome());
      stmt.setString(4, cliente.getTelefone());
      stmt.setString(5, cliente.getEndereco());

    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
  }

  @Override
  protected void setUpdateStatementValues(PreparedStatement stmt, Cliente entity)
      throws DataBaseException {
    Cliente cliente = convertObject(entity);
    try {
      stmt.setString(1, cliente.getCpf());
      stmt.setString(2, cliente.getNome());
      stmt.setString(3, cliente.getSobreNnome());
      stmt.setString(4, cliente.getTelefone());
      stmt.setString(5, cliente.getEndereco());
      stmt.setLong(6, cliente.getCodigo());

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
  protected Cliente convertObject(Cliente entity) {
    return entity;
  }

  @Override
  public String getNomeTabela() {
    return Cliente.class.getSimpleName().toLowerCase();
  }

  @Override
  protected void alterar(Cliente entity, Cliente target) {
    target.setNome(entity.getNome());
    target.setSobreNnome(entity.getSobreNnome());
    target.setCpf(entity.getCpf());
    target.setTelefone(entity.getTelefone());
    target.setEndereco(entity.getEndereco());
  }

  @Override
  protected Cliente getObject(ResultSet resultSet) throws SQLException {
    return ClienteBuilder.builder()
        .codigo(resultSet.getLong("codigo"))
        .cpf(resultSet.getString("cpf"))
        .nome(resultSet.getString("nome"))
        .sobrenome(resultSet.getString("sobrenome"))
        .telefone(resultSet.getString("telefone"))
        .endereco(resultSet.getString("endereco"))
        .build();
  }
}
