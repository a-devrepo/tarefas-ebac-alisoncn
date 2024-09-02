package com.sudy.projeto.domain.dao.impl;

import com.sudy.projeto.domain.dao.generic.ImplGenericDAO;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.exceptions.ChaveNaoEncontradaException;
import com.sudy.projeto.exceptions.DataBaseException;
import com.sudy.projeto.exceptions.TabelaNaoEncontradaException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImplClienteDAO extends ImplGenericDAO<Cliente, String> implements IClienteDAO {

  @Override
  protected String getInsertQuery() throws TabelaNaoEncontradaException {
    return new StringBuilder()
        .append("INSERT INTO ")
        .append(getNomeTabela())
        .append(" (id,nome,cpf,telefone,endereco,numero,cidade,estado,status)")
        .append(" VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?,?)")
        .toString();
  }

  @Override
  protected String getUpdateQuery()
      throws ChaveNaoEncontradaException, TabelaNaoEncontradaException {
    return new StringBuilder()
        .append("UPDATE ")
        .append(getNomeTabela())
        .append(" SET nome = ?,")
        .append(" telefone = ?,")
        .append(" endereco = ?,")
        .append(" numero = ?,")
        .append(" cidade = ?,")
        .append(" estado = ?,")
        .append(" status = ?")
        .append(" WHERE ")
        .append(getNomeCampoChave())
        .append(" = ?")
        .toString();
  }

  @Override
  protected String getfindQuery() throws ChaveNaoEncontradaException, TabelaNaoEncontradaException {
    return new StringBuilder()
        .append("SELECT * FROM ")
        .append(getNomeTabela())
        .append(" WHERE ")
        .append(getNomeCampoChave())
        .append(" = ?")
        .toString();
  }

  @Override
  protected String getRemoveQuery()
      throws ChaveNaoEncontradaException, TabelaNaoEncontradaException {
    return new StringBuilder()
        .append("DELETE FROM ")
        .append(getNomeTabela())
        .append(" WHERE ")
        .append(getNomeCampoChave())
        .append(" = ?")
        .toString();
  }

  @Override
  protected void setInsertStatementValues(PreparedStatement stmt, Cliente entity)
      throws DataBaseException {
    try {
      stmt.setString(1, entity.getNome());
      stmt.setString(2, entity.getCpf());
      stmt.setString(3, entity.getTelefone());
      stmt.setString(4, entity.getEndereco());
      stmt.setString(5, entity.getNumero());
      stmt.setString(6, entity.getCidade());
      stmt.setString(7, entity.getEstado());
      stmt.setString(8, entity.getStatus().name());
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros para inserção: " + e.getMessage());
    }
  }

  @Override
  protected void setUpdateStatementValues(PreparedStatement stmt, Cliente entity)
      throws DataBaseException {
    try {
      stmt.setString(1, entity.getNome());
      stmt.setString(2, entity.getTelefone());
      stmt.setString(3, entity.getEndereco());
      stmt.setString(4, entity.getNumero());
      stmt.setString(5, entity.getCidade());
      stmt.setString(6, entity.getEstado());
      stmt.setString(7, entity.getStatus().name());
      stmt.setString(8, entity.getCpf());
    } catch (SQLException e) {
      throw new DataBaseException(
          "Erro ao informar parâmetros para atualização: " + e.getMessage());
    }
  }

  @Override
  protected void setFindStatementValues(PreparedStatement stmt, String cpf)
      throws DataBaseException {
    try {
      stmt.setString(1, cpf);
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros de busca: " + e.getMessage());
    }
  }

  @Override
  protected void setRemoveStatementValues(PreparedStatement stmt, String cpf)
      throws DataBaseException {
    try {
      stmt.setString(1, cpf);
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros de exclusão: " + e.getMessage());
    }
  }

  @Override
  protected void alterar(Cliente entity, Cliente target) {
    target.setNome(entity.getNome());
    target.setTelefone(entity.getTelefone());
    target.setEndereco(entity.getEndereco());
    target.setNumero(entity.getNumero());
    target.setCidade(entity.getCidade());
    target.setEstado(entity.getEstado());
    target.setStatus(entity.getStatus());
  }

  @Override
  protected Class<Cliente> getTipoClazz() {
    return Cliente.class;
  }
}
