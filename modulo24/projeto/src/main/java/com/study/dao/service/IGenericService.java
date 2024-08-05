package com.study.dao.service;

public interface IGenericService<T, U> {
  public T cadastrar(T entity);
  public void atualizar(T entity);
  public T buscar(U code);
  public void remover(U code);
}
