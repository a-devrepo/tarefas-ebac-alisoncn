package com.sudy.projeto.domain.dao.impl;

import com.sudy.projeto.config.ConnectionFactory;
import com.sudy.projeto.domain.dao.generic.ImplGenericDAO;
import com.sudy.projeto.domain.factory.ItemVendaFactory;
import com.sudy.projeto.domain.factory.VendaFactory;
import com.sudy.projeto.domain.model.ItemVenda;
import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.domain.model.Venda;
import com.sudy.projeto.enums.StatusVenda;
import com.sudy.projeto.exceptions.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ImplVendaDAO extends ImplGenericDAO<Venda, String> implements IVendaDAO {

  @Override
  public Boolean salvar(Venda entity) throws DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet keys = null;
    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getfindQuery());
      verificarDuplicidade(stmt, entity);
      stmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS);
      setInsertStatementValues(stmt, entity);
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected > 0) {
        keys = stmt.getGeneratedKeys();
        if (keys.next()) {
          Persistente persistente = entity;
          persistente.setId(keys.getLong(1));
        }
        for (ItemVenda itemVenda : entity.getItens()) {
          stmt =
              connection.prepareStatement(getItemVendaInsertQuery(), Statement.RETURN_GENERATED_KEYS);
          cadastrarItemsVenda(stmt, entity, itemVenda);
        }
        return true;
      }
      return false;
    } catch (SQLException
        | DataBaseException
        | TabelaNaoEncontradaException
        | ChaveNaoEncontradaException e) {
      throw new DAOException("Erro ao cadastrar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, keys);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão: " + e.getMessage());
      }
    }
  }

  private void verificarDuplicidade(PreparedStatement statement, Venda venda) throws DAOException {
    try {
      setFindStatementValues(statement, venda.getCodigo());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        throw new DAOException("Já existe registro com o código: " + venda.getCodigo());
      }
    } catch (SQLException e) {
      throw new DAOException("Erro ao verificar duplicidade de registro: "+ e.getMessage());
    }
  }

  private void cadastrarItemsVenda(PreparedStatement statement, Venda venda, ItemVenda item)
      throws SQLException {
    setItemVendaInsertStatementValues(
        statement,
        venda.getId(),
        item.getProduto().getId(),
        item.getQuantidade(),
        item.getValorTotal());
    int rowsAffected = statement.executeUpdate();
    if (rowsAffected > 0) {
      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        item.setId(generatedKeys.getLong(1));
      }
    }
  }

  @Override
  public Venda buscar(String code) throws RegistroNaoEncontradoException, DAOException {
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement stmt = null;
    Venda entity = null;

    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getfindQuery());
      setFindStatementValues(stmt, code);
      resultSet = stmt.executeQuery();
      if (resultSet.next()) {
        entity = (Venda) new VendaFactory().create(resultSet);
        buscarItemsVendaDe(entity, connection);
      }
    } catch (SQLException | ChaveNaoEncontradaException | TabelaNaoEncontradaException e) {
      throw new DAOException("Erro ao buscar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, resultSet);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
    if (Objects.isNull(entity)) {
      throw new RegistroNaoEncontradoException("Registro não encontrado");
    }
    return entity;
  }

  public Boolean alterarQuantidadeProdutosVenda(Venda venda, String codigoProduto)
      throws RegistroNaoEncontradoException, DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;

    try {
      ItemVenda itemVenda = getItemVendaQuantidadeAlterada(venda, codigoProduto);
      if (Objects.isNull(itemVenda)) {
        throw new RegistroNaoEncontradoException("Registro não encontrado");
      }
      connection = ConnectionFactory.getConnection();
      stmt =connection.prepareStatement(getAlterarQuantidadeProdutosQuery(), Statement.RETURN_GENERATED_KEYS);
      setAlterarQuantidadeProdutosQuery(stmt, itemVenda.getId(), itemVenda.getQuantidade());
      stmt.executeUpdate();
      stmt = connection.prepareStatement(getAlterarValorTotalVendaQuery());
      alterarValorTotalVenda(stmt, venda.getValorTotal(), venda.getId());
      return true;
    } catch (SQLException e) {
      throw new DAOException("Erro ao alterar quantidade de produto da venda: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  public Boolean adicionarNovoProdutoAVenda(Venda venda, Produto produto, Integer quantidade)
      throws DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;

    try {
      connection = ConnectionFactory.getConnection();
      stmt =connection.prepareStatement(getItemVendaInsertQuery(), Statement.RETURN_GENERATED_KEYS);
      ItemVenda item = (ItemVenda) new ItemVendaFactory().create(produto, venda, quantidade);
      setItemVendaInsertStatementValues(stmt, venda.getId(), produto.getId(), quantidade, item.getValorTotal());
      stmt.executeUpdate();
      stmt = connection.prepareStatement(getAlterarValorTotalVendaQuery());
      alterarValorTotalVenda(stmt, venda.getValorTotal(), venda.getId());
      return true;
    } catch (SQLException e) {
      throw new DAOException("Erro ao adicionar novo produto a venda: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  private void alterarValorTotalVenda(PreparedStatement stmt, BigDecimal valorTotal, Long idVenda)
      throws SQLException {
    setAlterarValorTotalVendaStatementValues(stmt, valorTotal, idVenda);
    stmt.executeUpdate();
  }

  public Boolean removerProdutoDeVenda(Venda venda, Produto produto, Integer quantidade)
      throws DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;

    try {
      connection = ConnectionFactory.getConnection();
      ItemVenda itemVenda = getItemVendaQuantidadeAlterada(venda, produto.getCodigo());
      if (Objects.isNull(itemVenda)) {
        stmt = connection.prepareStatement(getRemoveItemVendaQuery());
        setRemoveItemStatementValues(stmt, produto.getId(), venda.getId());
        stmt.executeUpdate();
      } else {
        connection = ConnectionFactory.getConnection();
        stmt =connection.prepareStatement(getAlterarQuantidadeProdutosQuery(), Statement.RETURN_GENERATED_KEYS);
        setAlterarQuantidadeProdutosQuery(stmt, itemVenda.getId(), itemVenda.getQuantidade());
        stmt.executeUpdate();
      }
      stmt = connection.prepareStatement(getAlterarValorTotalVendaQuery());
      alterarValorTotalVenda(stmt, venda.getValorTotal(), venda.getId());
      return true;
    } catch (SQLException e) {
      throw new DAOException("Erro ao remover produto da venda: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  public Boolean removerTodosProdutosDeVenda(Venda venda)
          throws DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;

    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getRemoverTodosProdutosVendaQuery());
      setRemoverTodosProdutosStatementValues(stmt, venda.getId());
      stmt.executeUpdate();
      stmt = connection.prepareStatement(getAlterarValorTotalVendaQuery());
      alterarValorTotalVenda(stmt, venda.getValorTotal(), venda.getId());
      return true;
    } catch (SQLException e) {
      throw new DAOException("Erro ao remover todos os produtos da venda: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  private String getRemoverTodosProdutosVendaQuery() {
      return new StringBuilder()
              .append("DELETE FROM item_venda ")
              .append(" WHERE id_venda = ?")
              .toString();
  }

  private ItemVenda getItemVendaQuantidadeAlterada(Venda venda, String codigoProduto) {
    ItemVenda itemVenda =
        venda.getItens().stream()
            .filter(item -> item.getProduto().getCodigo().equals(codigoProduto))
            .findAny()
            .orElse(null);

    return itemVenda;
  }

  private void setAlterarQuantidadeProdutosQuery(
      PreparedStatement stmt, Long idItemVenda, Integer quantidade) throws SQLException {
    stmt.setInt(1, quantidade);
    stmt.setLong(2, idItemVenda);
  }

  private void setAlterarValorTotalVendaStatementValues(
      PreparedStatement stmt, BigDecimal valorTotal, Long idVenda) throws SQLException {
    stmt.setBigDecimal(1, valorTotal);
    stmt.setLong(2, idVenda);
  }

  private void setRemoveItemStatementValues(PreparedStatement stmt, Long idProduto, Long idVenda)
      throws SQLException {
    stmt.setLong(1, idProduto);
    stmt.setLong(2, idVenda);
  }

  private void setRemoverTodosProdutosStatementValues(PreparedStatement stmt, Long idVenda)
          throws SQLException {
    stmt.setLong(1, idVenda);
  }

  private String getRemoveItemVendaQuery() {
    return new StringBuilder()
        .append("DELETE FROM item_venda ")
        .append(" WHERE id_produto = ?")
            .append(" AND id_venda = ?")
        .toString();
  }

  private String getAlterarQuantidadeProdutosQuery() {
    return new StringBuilder()
        .append("UPDATE item_venda ")
        .append("SET quantidade = ?")
        .append(" WHERE id = ?")
        .toString();
  }

  private String getAlterarValorTotalVendaQuery() {
    return new StringBuilder()
        .append("UPDATE venda ")
        .append("SET valor_total = ?")
        .append(" WHERE id = ?")
        .toString();
  }

  private void buscarItemsVendaDe(Venda venda, Connection connection) throws DAOException {
    ResultSet resultSet = null;
    PreparedStatement stmt = null;

    try {
      stmt = connection.prepareStatement(getProdutoItemVendaJoinQuery());
      setProdutoItemVendaJoinStatementValues(stmt, venda);
      resultSet = stmt.executeQuery();
      Set<ItemVenda> items = new HashSet<>();
      while (resultSet.next()) {
        ItemVenda item = (ItemVenda) new ItemVendaFactory().create(resultSet);
        items.add(item);
      }
      venda.setItens(items);
    } catch (SQLException e) {
      throw new DAOException("Erro ao buscar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, resultSet);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  @Override
  public void cancelarVenda(Venda venda) throws DAOException {
    PreparedStatement stmt = null;
    Connection connection = null;
    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getCancelaVendaQuery());
      setCancelaVendaStatementValues(stmt, venda.getId());
      stmt.execute();
    } catch (SQLException e) {
      throw new DAOException("Erro ao buscar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  @Override
  public void concluirVenda(Venda venda) throws DAOException {
    PreparedStatement stmt = null;
    Connection connection = null;
    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getFinalizaVendaQuery());
      setFinalizaVendaStatementValues(stmt, venda.getId());
      stmt.execute();
    } catch (SQLException e) {
      throw new DAOException("Erro ao buscar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão" + e.getMessage());
      }
    }
  }

  private String getProdutoItemVendaJoinQuery() {
    StringBuilder query = new StringBuilder();
    return query
        .append("SELECT iv.id AS id_item_venda, iv.quantidade, iv.valor_total,")
        .append(" prod.id AS id_produto,prod.codigo,prod.nome,prod.descricao,prod.fabricante,prod.valor")
        .append(" FROM item_venda iv")
        .append(" INNER JOIN produto prod ON iv.id_produto = prod.id")
        .append(" WHERE iv.id_venda = ?")
        .toString();
  }

  private void setProdutoItemVendaJoinStatementValues(PreparedStatement statement, Venda venda)
      throws SQLException {
    statement.setLong(1, venda.getId());
  }

  private String getItemVendaInsertQuery() {
    StringBuilder query = new StringBuilder();
    return query
        .append("INSERT INTO item_venda (id,id_produto,id_venda,quantidade,valor_total)")
        .append("VALUES (nextval('sq_item_venda'),?,?,?,?)")
        .toString();
  }

  private void setItemVendaInsertStatementValues(
      PreparedStatement stmt,
      Long idVenda,
      Long idProduto,
      Integer quantidade,
      BigDecimal valorTotal)
      throws SQLException {
    stmt.setLong(1, idProduto);
    stmt.setLong(2, idVenda);
    stmt.setInt(3, quantidade);
    stmt.setBigDecimal(4, valorTotal);
  }

  @Override
  protected String getInsertQuery() throws TabelaNaoEncontradaException {
    return new StringBuilder()
        .append("INSERT INTO ")
        .append(getNomeTabela())
        .append(" (id,codigo,id_cliente,data_venda,valor_total,status_venda)")
        .append(" VALUES (nextval('sq_venda'),?,?,?,?,?)")
        .toString();
  }

  @Override
  protected String getUpdateQuery() {
    throw new UnsupportedOperationException("Operação não permitida");
  }

  @Override
  protected String getfindQuery() throws TabelaNaoEncontradaException, ChaveNaoEncontradaException {
    return clienteVendaQuery()
        .append(" WHERE ")
        .append("v.")
        .append(getNomeCampoChave())
        .append(" = ?")
        .toString();
  }

  private String getCancelaVendaQuery() {
    return "UPDATE venda SET status_venda = ? WHERE id = ?";
  }

  private void setCancelaVendaStatementValues(PreparedStatement statement, Long id)
      throws SQLException {
    statement.setString(1, StatusVenda.CANCELADA.name());
    statement.setLong(2, id);
  }

  private String getFinalizaVendaQuery() {
    return "UPDATE venda SET status_venda = ? WHERE id = ?";
  }

  private void setFinalizaVendaStatementValues(PreparedStatement statement, Long id)
      throws SQLException {
    statement.setString(1, StatusVenda.CONCLUIDA.name());
    statement.setLong(2, id);
  }

  private StringBuilder clienteVendaQuery() {
    StringBuilder query = new StringBuilder();
    query
        .append("SELECT c.id AS id_cliente, c.nome, c.cpf,")
        .append(" c.telefone, c.endereco, c.numero, c.cidade, c.estado, c.status,")
        .append(" v.id AS id_venda, v.codigo, v.valor_total, v.data_venda, v.status_venda")
        .append(" FROM cliente c ")
        .append("INNER JOIN venda v ON v.id_cliente = c.id");
    return query;
  }

  @Override
  protected String getRemoveQuery() {
    throw new UnsupportedOperationException("Operação não permitida");
  }

  @Override
  protected void setInsertStatementValues(PreparedStatement stmt, Venda entity)
      throws DataBaseException {
    try {
      stmt.setString(1, entity.getCodigo());
      stmt.setLong(2, entity.getCliente().getId());
      stmt.setTimestamp(3, Timestamp.from(entity.getDataVenda()));
      stmt.setBigDecimal(4, entity.getValorTotal());
      stmt.setString(5, entity.getStatusVenda().name());
    } catch (SQLException e) {
      throw new DataBaseException("Erro ao informar parâmetros para inserção:" + e.getMessage());
    }
  }

  @Override
  protected void setUpdateStatementValues(PreparedStatement stmt, Venda entity) {
    throw new UnsupportedOperationException("Operação não permitida");
  }

  @Override
  protected void setFindStatementValues(PreparedStatement stmt, String code) throws DAOException {
    try {
      stmt.setString(1, code);
    } catch (SQLException e) {
      throw new DAOException("Erro ao informar parâmetros para busca:" + e.getMessage());
    }
  }

  @Override
  protected void setRemoveStatementValues(PreparedStatement stmt, String code) {
    throw new UnsupportedOperationException("Operação não permitida");
  }

  @Override
  protected void alterar(Venda entity, Venda target) {
    throw new UnsupportedOperationException("Operação não permitida");
  }

  @Override
  protected Class<Venda> getTipoClazz() {
    return Venda.class;
  }
}
