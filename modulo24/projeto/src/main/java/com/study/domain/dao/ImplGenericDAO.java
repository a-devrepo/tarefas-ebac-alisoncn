package com.study.domain.dao;


import java.util.*;

public abstract class ImplGenericDAO<T,U> implements IGenericDAO<T,U>{

    private List<T> entities;

    public ImplGenericDAO() {
    this.entities = new ArrayList<T>();
    }

    @Override
    public T salvar(T entity) {
        if(Objects.isNull(entity)){
            throw new RuntimeException("Objeto não pode ser nulo");
        }
        this.gerarCodigo(entity);
        this.entities.add(entity);
        return entity;
    }

    @Override
    public T buscar(U code) {
        for(T entity: entities){
            if(getCodigo(entity) == code){
                return  entity;
            }
        }
        throw new RuntimeException("Registro não encontrado");
    }

    @Override
    public void atualizar(T entity) {
        if(Objects.isNull(entity)){
            throw new RuntimeException("Objeto não pode ser nulo");
        }
        T target = this.buscar(getCodigo(entity));
        
        this.atualizar(entity,target);
    }

    @Override
    public void excluir(U code) {
        T entity = this.buscar(code);
        this.entities.remove(entity);
    }

    protected abstract U getCodigo(T entity);

    protected abstract void gerarCodigo(T entity);

    protected abstract void atualizar(T entity, T target);
}
