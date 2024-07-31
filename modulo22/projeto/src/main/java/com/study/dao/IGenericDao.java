package com.study.dao;

import java.util.List;

public interface IGenericDao<T, S> {
  T cadastrar(T entity);

  T consultar(S code);

  void alterar(T entity, S code);

  void excluir(S code);

  List<T> listar();
}
