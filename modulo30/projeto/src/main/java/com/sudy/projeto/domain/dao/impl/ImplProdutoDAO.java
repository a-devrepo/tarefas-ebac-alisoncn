package com.sudy.projeto.domain.dao.impl;

import com.sudy.projeto.domain.dao.generic.ImplGenericDAO;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.exceptions.ChaveNaoEncontradaException;
import com.sudy.projeto.exceptions.DataBaseException;
import com.sudy.projeto.exceptions.TabelaNaoEncontradaException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImplProdutoDAO extends ImplGenericDAO<Produto, Long> implements IProdutoDAO {

  @Override
  protected String getInsertQuery() throws TabelaNaoEncontradaException {
    return new StringBuilder()
        .append("INSERT INTO ")
        .append(getNomeTabela())
        .append(" (id,codigo,nome,descricao,fabricante,valor)")
        .append(" VALUES (nextval('sq_produto'),?,?,?,?,?)")
        .toString();
  }

  @Override
  protected String getUpdateQuery()
      throws TabelaNaoEncontradaException, ChaveNaoEncontradaException {
    return new StringBuilder()
        .append("UPDATE ")
        .append(getNomeTabela())
        .append(" SET nome = ?,")
        .append(" descricao = ?,")
        .append(" fabricante = ?,")
        .append(" valor = ?")
        .append(" WHERE ")
        .append(getNomeCampoChave())
        .append(" = ?")
        .toString();
  }

  @Override
  protected String getfindQuery() throws TabelaNaoEncontradaException, ChaveNaoEncontradaException {
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
      throws TabelaNaoEncontradaException, ChaveNaoEncontradaException {
    return new StringBuilder()
        .append("DELETE FROM ")
        .append(getNomeTabela())
        .append(" WHERE ")
        .append(getNomeCampoChave())
        .append(" = ?")
        .toString();
  }

  @Override
  protected void setInsertStatementValues(PreparedStatement stmt, Produto entity)
      throws DataBaseException {
    try {
      stmt.setString(1, entity.getCodigo());
      stmt.setString(2, entity.getNome());
      stmt.setString(3, entity.getDescricao());
      stmt.setString(4, entity.getFabricante());
      stmt.setBigDecimal(5, new BigDecimal(entity.getValor().toString()));
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros para inserção:" + e.getMessage());
    }
  }

  @Override
  protected void setUpdateStatementValues(PreparedStatement stmt, Produto entity)
      throws DataBaseException {
    try {
      stmt.setString(1, entity.getNome());
      stmt.setString(2, entity.getDescricao());
      stmt.setString(3, entity.getFabricante());
      stmt.setBigDecimal(4, new BigDecimal(entity.getValor().toString()));
      stmt.setLong(5, entity.getId());
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros para atualização:" + e.getMessage());
    }
  }

  @Override
  protected void setFindStatementValues(PreparedStatement stmt, Long code)
      throws DataBaseException {
    try {
      stmt.setLong(1, code);
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros para busca:" + e.getMessage());
    }
  }

  @Override
  protected void setRemoveStatementValues(PreparedStatement stmt, Long code)
      throws DataBaseException {
    try {
      stmt.setLong(1, code);
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros para exclusão:" + e.getMessage());
    }
  }

  @Override
  protected void alterar(Produto entity, Produto target) {
    target.setNome(entity.getNome());
    target.setDescricao(entity.getDescricao());
    target.setFabricante(entity.getFabricante());
    target.setValor(entity.getValor());
  }

  @Override
  protected Class<Produto> getTipoClazz() {
    return Produto.class;
  }
}
