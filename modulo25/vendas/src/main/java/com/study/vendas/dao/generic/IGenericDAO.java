package com.study.vendas.dao.generic;

import com.study.vendas.dao.Persistente;
import com.study.vendas.exceptions.CodigoNaoEncontradoException;
import com.study.vendas.exceptions.DataBaseException;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import java.util.Collection;

public interface IGenericDAO<T extends Persistente, U> {
  Collection<T> listar();
  T salvar(T entity) throws CodigoNaoEncontradoException, DataBaseException;
  T buscar(U code) throws RegistroNaoEncontradoException;
  void excluir(U code) throws RegistroNaoEncontradoException;
  void atualizar(T entity) throws CodigoNaoEncontradoException, RegistroNaoEncontradoException;
}
