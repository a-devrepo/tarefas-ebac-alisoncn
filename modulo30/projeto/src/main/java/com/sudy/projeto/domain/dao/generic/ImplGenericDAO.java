package com.sudy.projeto.domain.dao.generic;

import com.sudy.projeto.anotacoes.Chave;
import com.sudy.projeto.anotacoes.Coluna;
import com.sudy.projeto.anotacoes.Tabela;
import com.sudy.projeto.config.ConnectionFactory;
import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.enums.StatusCliente;
import com.sudy.projeto.exceptions.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class ImplGenericDAO<T extends Persistente, U> implements IGenericDAO<T, U> {

  protected abstract String getInsertQuery() throws TabelaNaoEncontradaException;

  protected abstract String getUpdateQuery()
      throws ChaveNaoEncontradaException, TabelaNaoEncontradaException, UnsupportedOperationException;

  protected abstract String getfindQuery()
      throws ChaveNaoEncontradaException, TabelaNaoEncontradaException;

  protected abstract String getRemoveQuery()
      throws ChaveNaoEncontradaException, TabelaNaoEncontradaException, UnsupportedOperationException;

  protected abstract void setInsertStatementValues(PreparedStatement stmt, T entity)
      throws SQLException, DataBaseException;

  protected abstract void setUpdateStatementValues(PreparedStatement stmt, T entity)
      throws DataBaseException, UnsupportedOperationException;

  protected abstract void setFindStatementValues(PreparedStatement stmt, U code)
      throws SQLException, DataBaseException, DAOException;

  protected abstract void setRemoveStatementValues(PreparedStatement stmt, U code)
      throws SQLException, DataBaseException, UnsupportedOperationException;
  
  protected abstract void alterar(T entity, T target);

  protected abstract Class<T> getTipoClazz();


  @Override
  public Collection<T> listar() throws DAOException {
    List<T> lista = new ArrayList<>();
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement statement = null;
    T entity = null;
    try {
      String query = String.format("SELECT * FROM %s ", this.getNomeTabela());
      connection = ConnectionFactory.getConnection();
      statement = connection.prepareStatement(query);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        entity = getObject(resultSet);
        lista.add(entity);
      }
    } catch (InstantiationException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException
        | TipoElementoNaoConhecidoException
        | TabelaNaoEncontradaException
        | SQLException e) {
      throw new DAOException("Erro ao listar objetos: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, statement, resultSet);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão: " + e.getMessage());
      }
    }
    return lista;
  }

  @Override
  public Boolean salvar(T entity) throws DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;
    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS);
      setInsertStatementValues(stmt, entity);
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected > 0) {
        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
          Persistente persistente = entity;
          persistente.setId(keys.getLong(1));
        }
        return true;
      }
      return false;
    } catch (SQLException | DataBaseException | TabelaNaoEncontradaException e) {
      throw new DAOException("Erro ao cadastrar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão: " + e.getMessage());
      }
    }
  }

  @Override
  public T buscar(U code) throws RegistroNaoEncontradoException, DAOException {
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement stmt = null;
    T entity = null;

    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getfindQuery());
      setFindStatementValues(stmt, code);
      resultSet = stmt.executeQuery();
      if (resultSet.next()) {
        entity = getObject(resultSet);
      }
    } catch (SQLException
        | DataBaseException
        | ChaveNaoEncontradaException
        | TabelaNaoEncontradaException
        | NoSuchMethodException
        | InvocationTargetException
        | InstantiationException
        | IllegalAccessException
        | TipoElementoNaoConhecidoException e) {
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

  @Override
  public void excluir(U code) throws DAOException {
    Connection connection = null;
    PreparedStatement stmt = null;
    try {
      connection = ConnectionFactory.getConnection();
      stmt = connection.prepareStatement(getRemoveQuery());
      setRemoveStatementValues(stmt, code);
      stmt.execute();
    } catch (SQLException
        | DataBaseException
        | ChaveNaoEncontradaException
        | TabelaNaoEncontradaException e) {
      throw new DAOException("Erro ao excluir registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, stmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão " + e.getMessage());
      }
    }
  }

  @Override
  public void atualizar(T entity) throws RegistroNaoEncontradoException, DAOException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      U chave = getChave(entity);
      T old = this.buscar(chave);
      connection = ConnectionFactory.getConnection();
      updateStmt = connection.prepareStatement(getUpdateQuery());
      this.alterar(entity, old);
      setUpdateStatementValues(updateStmt, old);
      updateStmt.execute();
    } catch (SQLException
        | DataBaseException
        | ChaveNaoEncontradaException
        | TabelaNaoEncontradaException e) {
      throw new DAOException("Erro ao atualizar registro: " + e.getMessage());
    } finally {
      try {
        ConnectionFactory.closeConnection(connection, updateStmt, null);
      } catch (SQLException e) {
        throw new DAOException("Erro ao fechar conexão: " + e.getMessage());
      }
    }
  }

  private T getObject(ResultSet resultSet)
          throws SQLException,
          NoSuchMethodException,
          InvocationTargetException,
          InstantiationException,
          IllegalAccessException,
          TipoElementoNaoConhecidoException {
    T entity = getTipoClazz().getConstructor().newInstance();
    Field[] fields = entity.getClass().getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(Coluna.class)) {
        Coluna anotacao = field.getAnnotation(Coluna.class);
        String nomeColuna = anotacao.dbName();
        String nomeMetodoSet = anotacao.setJavaName();
        Class<?> campoClazz = field.getType();
        Method metodo = entity.getClass().getMethod(nomeMetodoSet, campoClazz);
        setValueByType(entity, metodo, campoClazz, resultSet, nomeColuna);
      }
    }
    return entity;
  }

  protected String getNomeTabela() throws TabelaNaoEncontradaException {
    if (getTipoClazz().isAnnotationPresent(Tabela.class)) {
      Tabela tabela = getTipoClazz().getAnnotation(Tabela.class);
      return tabela.value();
    } else {
      throw new TabelaNaoEncontradaException(
              "Tabela " + getTipoClazz().getName() + " não encontrada");
    }
  }

  public U getChave(T entity) throws ChaveNaoEncontradaException {
    Field[] declaredFields = entity.getClass().getDeclaredFields();
    U chave = null;
    try {
      for (Field field : declaredFields) {
        if (field.isAnnotationPresent(Chave.class)) {
          Chave anotacao = field.getAnnotation(Chave.class);
          String nomeMetodo = anotacao.value();
          Method metodo = entity.getClass().getMethod(nomeMetodo);
          chave = (U) metodo.invoke(entity);
        }
      }
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new ChaveNaoEncontradaException("Chave do objeto: " + getClass() + " não encontrada");
    }
    if (Objects.isNull(chave)) {
      throw new ChaveNaoEncontradaException("Chave do objeto: " + getClass() + " não encontrada");
    }
    return chave;
  }

  public String getNomeCampoChave() throws ChaveNaoEncontradaException {
    Class<?> clazz = getTipoClazz();
    Field[] fields = clazz.getDeclaredFields();
    String nomeColuna = null;
    for (Field field : fields) {
      if (field.isAnnotationPresent(Chave.class) && field.isAnnotationPresent(Coluna.class)) {
        Coluna coluna = field.getAnnotation(Coluna.class);
        nomeColuna = coluna.dbName();
      }
    }
    if (Objects.isNull(nomeColuna)) {
      throw new ChaveNaoEncontradaException(
              "Chave da classe: " + clazz.getSimpleName() + " não encontrada");
    }
    return nomeColuna;
  }

  private void setValueByType(
          T entity, Method metodo, Class<?> campoClazz, ResultSet resultSet, String nomeColuna)
          throws SQLException,
          InvocationTargetException,
          IllegalAccessException,
          TipoElementoNaoConhecidoException {

    if (campoClazz.equals(Integer.class)) {
      Integer value = resultSet.getInt(nomeColuna);
      metodo.invoke(entity, value);
    } else if (campoClazz.equals(Long.class)) {
      Long value = resultSet.getLong(nomeColuna);
      metodo.invoke(entity, value);
    } else if (campoClazz.equals(Double.class)) {
      Double value = resultSet.getDouble(nomeColuna);
      metodo.invoke(entity, value);
    } else if (campoClazz.equals(Short.class)) {
      Short value = resultSet.getShort(nomeColuna);
      metodo.invoke(entity, value);
    } else if (campoClazz.equals(BigDecimal.class)) {
      BigDecimal value = resultSet.getBigDecimal(nomeColuna);
      metodo.invoke(entity, value);
    } else if (campoClazz.equals(String.class)) {
      String value = resultSet.getString(nomeColuna);
      metodo.invoke(entity, value);
    }else if (campoClazz.equals(StatusCliente.class)) {
      StatusCliente value = StatusCliente.valueOf(resultSet.getString(nomeColuna));
      metodo.invoke(entity, value);
    }
    else {
      throw new TipoElementoNaoConhecidoException(
              "Tipo de classe não conhecido: " + campoClazz.getSimpleName());
    }
  }
}
