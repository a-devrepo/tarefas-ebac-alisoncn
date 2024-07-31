package com.study.service;

import com.study.dao.GenericDao;
import com.study.dao.IGenericDao;
import java.util.List;
import java.util.Objects;

public abstract class GenericService<T, S> implements IGenericService<T, S> {

  protected IGenericDao<T, S> dao;

  public GenericService(GenericDao<T, S> dao) {
    this.dao = dao;
  }

  public T cadastrar(T entity) {
    T saved = this.dao.cadastrar(entity);
    Objects.requireNonNullElseGet(saved, () -> new RuntimeException("Erro ao cadastrar"));
    return saved;
  }

  public List<T> listar() {
    List<T> entities = dao.listar();
    if (entities.isEmpty()) {
      throw new RuntimeException("Sem dados para exibir");
    }
    return entities;
  }
}
