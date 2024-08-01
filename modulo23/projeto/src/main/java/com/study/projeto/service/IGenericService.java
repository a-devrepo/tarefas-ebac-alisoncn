package com.study.projeto.service;

import java.util.List;

public interface IGenericService<T, S> {

  public T cadastrar(T entity);

  public List<T> listar();
}
