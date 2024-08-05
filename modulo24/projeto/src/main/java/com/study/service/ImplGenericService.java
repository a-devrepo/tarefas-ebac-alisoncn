package com.study.service;

import com.study.domain.dao.IGenericDAO;
import com.study.domain.dao.ImplGenericDAO;

public class ImplGenericService<T,U> implements IGenericService<T,U>{

    protected IGenericDAO<T,U> dao;

    public ImplGenericService(ImplGenericDAO<T,U> dao){
        this.dao = dao;
    }

    @Override
    public T cadastrar(T entity) {
        entity = dao.salvar(entity);
        return entity;
    }

    @Override
    public void atualizar(T entity) {
        dao.atualizar(entity);
    }

    @Override
    public T buscar(U code) {
        return dao.buscar(code);
    }

    @Override
    public void remover(U code) {
        dao.excluir(code);
    }
}
