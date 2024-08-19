package com.sudy.projeto.domain.dao.generic;

import com.sudy.projeto.exceptions.CodigoNaoEncontradoException;
import com.sudy.projeto.exceptions.DataBaseException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.sql.SQLException;
import java.util.List;

public interface IGenericDAO<T, U> {
  List<T> listar() throws DataBaseException;
  int salvar(T entity) throws CodigoNaoEncontradoException, DataBaseException, IllegalAccessException, SQLException;
  T buscar(U code) throws RegistroNaoEncontradoException, DataBaseException;
  void excluir(U code) throws RegistroNaoEncontradoException, DataBaseException;
  void atualizar(T entity) throws CodigoNaoEncontradoException, RegistroNaoEncontradoException, DataBaseException;
}
