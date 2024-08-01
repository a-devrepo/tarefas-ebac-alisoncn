package com.study.projeto.service;

import com.study.projeto.domain.dao.IGenericDAO;
import com.study.projeto.domain.dao.ImplGenericDAO;
import java.util.List;
import java.util.Objects;

public abstract class ImplGenericService<T, S> implements IGenericService<T, S> {

  protected IGenericDAO<T, S> dao;

  public ImplGenericService(ImplGenericDAO<T, S> dao) {
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
