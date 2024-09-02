package com.sudy.projeto.services.generic;

import com.sudy.projeto.domain.dao.generic.IGenericDAO;
import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.util.Collection;

public abstract class ImplGenericService<T extends Persistente, U>
    implements IGenericService<T, U> {

  protected IGenericDAO<T, U> dao;

  public ImplGenericService(IGenericDAO dao) {
    this.dao = dao;
  }

  @Override
  public Collection<T> listar() throws DAOException {
    return this.dao.listar();
  }

  @Override
  public Boolean salvar(T entity) throws DAOException {
    return this.dao.salvar(entity);
  }

  @Override
  public T buscar(U code) throws DAOException, RegistroNaoEncontradoException {
    return this.dao.buscar(code);
  }

  @Override
  public void atualizar(T entity) throws DAOException, RegistroNaoEncontradoException {
    this.dao.atualizar(entity);
  }

  @Override
  public void excluir(U code) throws DAOException {
    this.dao.excluir(code);
  }


}
