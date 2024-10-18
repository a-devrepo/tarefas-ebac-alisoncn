package com.study.projeto.services.generic;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.dao.generic.ImplGenericDAO;
import com.study.projeto.domain.model.Persistente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroDuplicadoException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;

import java.io.Serializable;
import java.util.Collection;

public abstract class ImplGenericService<T extends Persistente, E extends Serializable>
    implements IGenericService<T, E> {

  protected IGenericDAO<T, E> dao;

  public ImplGenericService(IGenericDAO<T,E> dao) {
    this.dao = dao;
  }

  @Override
  public Collection<T> listar() throws DAOException {
    return this.dao.listar();
  }

  @Override
  public T salvar(T entity) throws DAOException{
    return this.dao.salvar(entity);
  }

  @Override
  public T buscar(E code) throws DAOException, RegistroNaoEncontradoException {
    return this.dao.buscar(code);
  }

  @Override
  public T atualizar(T entity) throws DAOException{
    return this.dao.atualizar(entity);
  }

  @Override
  public void excluir(T entity) throws DAOException {
    this.dao.excluir(entity);
  }
}
