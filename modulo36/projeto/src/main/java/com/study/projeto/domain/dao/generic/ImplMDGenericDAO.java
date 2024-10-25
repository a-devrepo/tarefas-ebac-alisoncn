package com.study.projeto.domain.dao.generic;

import com.study.projeto.domain.dao.util.DataStoreConfig;
import com.study.projeto.domain.model.Persistente;
import com.study.projeto.domain.model.nonrelational.ClienteMD;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import dev.morphia.Datastore;
import dev.morphia.query.experimental.filters.Filters;
import java.io.Serializable;
import java.util.Collection;

public abstract class ImplMDGenericDAO<T extends Persistente, E extends Serializable>
    implements IGenericDAO<T, E> {

  protected Datastore datastore;
  private final Class<T> persistenteClass;

  public ImplMDGenericDAO(Class<T> persistenteClass) {
    this.persistenteClass = persistenteClass;
    this.datastore = DataStoreConfig.getDataStore();
  }

  @Override
  public Collection<T> listar() {
    return datastore.find(this.persistenteClass).stream().toList();
  }

  @Override
  public T salvar(T entity) throws DAOException{
    try {
      datastore.save(entity);
      return entity;
    } catch (Exception e) {
      throw new DAOException("Erro ao salvar registro: ", e);
    }
  }

  @Override
  public T buscar(E code) throws RegistroNaoEncontradoException, DAOException {
    T result = null;
    try {
      result =
              (T) datastore
                      .find(ClienteMD.class).filter(Filters.eq("id",code)).iterator().tryNext();
    } catch (Exception e) {
      throw new DAOException("Erro ao buscar registro: ", e);
    }
    if (result == null) {
      throw new RegistroNaoEncontradoException("Registro não encontrado");
    }
    return result;
  }

  @Override
  public void excluir(T entity) throws DAOException {
    try {
      datastore.delete(entity);
    } catch (Exception e) {
      throw new DAOException("Erro ao excluir registro: ", e);
    }
  }

  @Override
  public abstract T atualizar(T entity) throws DAOException;
}
