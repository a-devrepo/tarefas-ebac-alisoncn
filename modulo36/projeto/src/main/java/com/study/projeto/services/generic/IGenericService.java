package com.study.projeto.services.generic;

import com.study.projeto.domain.model.Persistente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroDuplicadoException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericService<T extends Persistente, E extends Serializable> {
  public Collection<T> listar() throws DAOException;
  public T salvar(T entity) throws DAOException;
  public T buscar(E code) throws DAOException, RegistroNaoEncontradoException;
  public T atualizar(T entity) throws DAOException, RegistroNaoEncontradoException;
  public void excluir(T entity) throws DAOException;
}
