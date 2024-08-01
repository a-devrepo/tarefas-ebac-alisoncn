package com.study.projeto.domain.dao;

import java.util.ArrayList;
import java.util.List;

public abstract class ImplGenericDAO<T, S> implements IGenericDAO<T, S> {

  private List<T> entities;

  public ImplGenericDAO() {
    this.entities = new ArrayList<T>();
  }

  @Override
  public T cadastrar(T entity) {
    gerarId(entity);
    this.entities.add(entity);
    return entity;
  }

  @Override
  public T consultar(S code) {
    for (T entity : entities) {
      if (getCodigo(entity) == code) {
        return entity;
      }
    }
    return null;
  }

  @Override
  public void alterar(T entity, S code) {
    T target = consultar(code);
    atualizar(entity, target);
    cadastrar(target);
  }

  @Override
  public void excluir(S code) {
    T entity = consultar(code);
    this.entities.remove(entity);
  }

  @Override
  public List<T> listar() {
    return this.entities;
  }

  protected abstract void atualizar(T entity, T target);

  protected abstract void gerarId(T entity);

  protected abstract S getCodigo(T entity);
}
