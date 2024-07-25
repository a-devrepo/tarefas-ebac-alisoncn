package com.study.projeto;

import java.util.List;

public interface IGenericList<T extends Persistente, E> {

    T cadastrar(T entity);

    T consultar(E code);

    void alterar(T entity, E code);

    void excluir(E code);

    List<T> listar();
}
