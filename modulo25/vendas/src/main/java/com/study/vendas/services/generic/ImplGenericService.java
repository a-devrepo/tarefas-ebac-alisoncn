package com.study.vendas.services.generic;

import com.study.vendas.dao.Persistente;
import com.study.vendas.dao.generic.IGenericDAO;
import com.study.vendas.exceptions.CodigoNaoEncontradoException;
import com.study.vendas.exceptions.DataBaseException;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import java.util.Collection;

public class ImplGenericService<T extends Persistente, U> implements IGenericService<T, U> {

  IGenericDAO<T, U> dao;

  public ImplGenericService(IGenericDAO<T, U> dao) {
    this.dao = dao;
  }

  @Override
  public Collection<T> listar() {
    return dao.listar();
  }

  @Override
  public T cadastrar(T entity) throws DataBaseException, CodigoNaoEncontradoException {
    return dao.salvar(entity);
  }

  @Override
  public void atualizar(T entity)
      throws CodigoNaoEncontradoException, RegistroNaoEncontradoException {
    dao.atualizar(entity);
  }

  @Override
  public T buscar(U code) throws RegistroNaoEncontradoException {
    return dao.buscar(code);
  }

  @Override
  public void excluir(U code) throws RegistroNaoEncontradoException {
    dao.excluir(code);
  }
}
