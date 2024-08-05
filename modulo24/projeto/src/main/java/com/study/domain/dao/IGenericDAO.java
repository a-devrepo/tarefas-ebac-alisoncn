package com.study.domain.dao;

public interface IGenericDAO<T,U> {
    T salvar(T entity);
    T buscar(U code);
    void atualizar(T entity);
    void excluir(U code);
}
