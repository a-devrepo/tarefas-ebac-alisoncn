package com.study.projeto;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericList<T extends Persistente, E> implements IGenericList<T, E> {

    private List<T> entities;

    public GenericList() {
        this.entities = new ArrayList<>();
    }

    @Override
    public T cadastrar(T entity) {
        gerarId(entity);
        this.entities.add(entity);
        return entity;
    }

    @Override
    public T consultar(E code) {
        for (T entity : entities) {
            if (entity.getCodigo() == code) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void alterar(T entity, E code) {
        T target = consultar(code);
        atualizar(entity, target);
        cadastrar(target);
    }

    @Override
    public void excluir(E code) {
        T entity = consultar(code);
        this.entities.remove(entity);
    }

    @Override
    public List<T> listar() {
        return this.entities;
    }

    protected abstract void atualizar(T entity, T target);

    protected abstract void gerarId(T entity);
}
