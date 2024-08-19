package com.sudy.projeto.domain.dao.generic;

import com.sudy.projeto.anotacoes.Codigo;
import com.sudy.projeto.config.ConnectionFactory;
import com.sudy.projeto.exceptions.CodigoNaoEncontradoException;
import com.sudy.projeto.exceptions.DataBaseException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ImplGenericDAO<T,U> implements IGenericDAO<T,U> {

    protected abstract String getInsertQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getfindQuery();
    protected abstract String getRemoveQuery();
    protected abstract void setInsertStatementValues(PreparedStatement stmt, T entity) throws DataBaseException;
    protected abstract void setUpdateStatementValues(PreparedStatement stmt, T entity) throws DataBaseException;
    protected abstract void setFindStatementValues(PreparedStatement stmt, U code) throws DataBaseException;
    protected abstract void setRemoveStatementValues(PreparedStatement stmt, U code) throws DataBaseException;
    protected abstract T convertObject(T entity);
    public abstract String getNomeTabela();
    protected abstract void alterar(T entity, T target);
    protected abstract T getObject(ResultSet resultSet) throws SQLException;

    @Override
    public List<T> listar() throws DataBaseException {
        Connection connection = null;
        String query = String.format("SELECT * FROM %s ", this.getNomeTabela());
        List lista = new ArrayList();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        T t = null;
        try{
            connection = ConnectionFactory.getConnection();
             statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                t = getObject(resultSet);
                lista.add(t);
            }
        }catch (Exception e){
            throw new DataBaseException(e.getMessage());
        } finally{
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
        throw new DataBaseException(e.getMessage());
            }

        }
        return lista;
    }


    @Override
    public int salvar(T entity) throws DataBaseException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        int id = 0;
        try{
            connection = ConnectionFactory.getConnection();
            stmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS);
            setInsertStatementValues(stmt,entity);
            stmt.executeUpdate();
            resultSet = stmt.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            return id;
        }catch (Exception e){
            throw new DataBaseException(e.getMessage());
        } finally{
            try {
                resultSet.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                throw new DataBaseException(e.getMessage());
            }
        }
    }

    @Override
    public T buscar(U code) throws RegistroNaoEncontradoException, DataBaseException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        T entity = null;

        try{
            connection = ConnectionFactory.getConnection();
            stmt = connection.prepareStatement(getfindQuery());
            setFindStatementValues(stmt,code);
            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                entity = getObject(resultSet);
            }
        }catch (Exception e){
            throw new DataBaseException(e.getMessage());
        } finally{
            try {
                resultSet.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                throw new DataBaseException(e.getMessage());
            }
        }
        if(Objects.isNull(entity)){
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        }
        return entity;
    }

    @Override
    public void excluir(U code) throws DataBaseException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.prepareStatement(getRemoveQuery());
            setRemoveStatementValues(stmt,code);
            stmt.execute();
        } catch (Exception e){
            throw new DataBaseException(e.getMessage());
        } finally{
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }


    }

    @Override
    public void atualizar(T entity) throws CodigoNaoEncontradoException
            , DataBaseException, RegistroNaoEncontradoException {
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            U codigo = getCodigo(entity);
            T old = this.buscar(codigo);
            connection = ConnectionFactory.getConnection();
            updateStmt = connection.prepareStatement(getUpdateQuery());
            this.alterar(entity,old);
            setUpdateStatementValues(updateStmt,old);
            updateStmt.execute();
        }
        catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally{
        try {
            updateStmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
    }


    protected U getCodigo(T entity) throws CodigoNaoEncontradoException {
        U codigo = null;
        for (Field field : entity.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Codigo.class)){
                Codigo anotacao = field.getAnnotation(Codigo.class);
                String nomeMetodo = anotacao.value();
                try{
                    Method metodo = entity.getClass().getMethod(nomeMetodo);
                    codigo = (U) metodo.invoke(entity);
                    break;
                }catch (Exception e){
                    throw new CodigoNaoEncontradoException("Valor do código do objeto "
                            + entity.getClass()+" não encontrado");
                }
            }
        }
        if(codigo == null){
            throw new CodigoNaoEncontradoException("Valor do código do objeto "
                    + entity.getClass()+" não encontrado");
        }
        return codigo;
    }

}

