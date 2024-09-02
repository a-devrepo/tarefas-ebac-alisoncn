package com.sudy.projeto.domain.dao.generic;

import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.util.Collection;

public interface IGenericDAO<T extends Persistente, U> {
  Collection<T> listar() throws DAOException;
  Boolean salvar(T entity) throws DAOException;
  T buscar(U code) throws RegistroNaoEncontradoException,DAOException;
  void excluir(U code) throws DAOException;
  void atualizar(T entity) throws RegistroNaoEncontradoException, DAOException;
}
