package com.study.service;

import com.study.domain.Pessoa;

import java.util.List;

public interface IGenericService<T, S> {
  public T cadastrar(T entity);

  public List<T> listar();
}
