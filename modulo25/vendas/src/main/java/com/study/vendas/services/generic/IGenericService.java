package com.study.vendas.services.generic;

import com.study.vendas.dao.Persistente;
import com.study.vendas.exceptions.CodigoNaoEncontradoException;
import com.study.vendas.exceptions.DataBaseException;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import java.util.Collection;

public interface IGenericService<T extends Persistente, U> {

  public Collection<T> listar();

  public T cadastrar(T entity) throws DataBaseException, CodigoNaoEncontradoException;

  public void atualizar(T entity)
      throws CodigoNaoEncontradoException, RegistroNaoEncontradoException;

  public T buscar(U code) throws RegistroNaoEncontradoException;

  public void excluir(U code) throws RegistroNaoEncontradoException;
}
