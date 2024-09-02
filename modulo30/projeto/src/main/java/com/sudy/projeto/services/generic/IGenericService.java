package com.sudy.projeto.services.generic;

import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;

import java.util.Collection;

public interface IGenericService<T extends Persistente, U> {
  public Collection<T> listar() throws DAOException;
  public Boolean salvar(T entity) throws DAOException;
  public T buscar(U code) throws DAOException, RegistroNaoEncontradoException;
  public void atualizar(T entity) throws DAOException, RegistroNaoEncontradoException;public void excluir(U code) throws DAOException;
}
