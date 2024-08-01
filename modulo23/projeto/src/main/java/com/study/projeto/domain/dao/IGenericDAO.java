package com.study.projeto.domain.dao;

import java.util.List;

public interface IGenericDAO<T, S> {
  T cadastrar(T entity);

  T consultar(S code);

  void alterar(T entity, S code);

  void excluir(S code);

  List<T> listar();
}
