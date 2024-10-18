package com.study.projeto.domain.dao.generic;

import com.study.projeto.domain.model.Persistente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroDuplicadoException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO<T extends Persistente, E extends Serializable> {
  Collection<T> listar();

  T salvar(T entity) throws DAOException;

  T buscar(E code) throws RegistroNaoEncontradoException, DAOException;

  void excluir(T entity) throws DAOException;

  T atualizar(T entity) throws DAOException;
}
