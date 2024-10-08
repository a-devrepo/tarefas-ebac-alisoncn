package br.com.estudojpa.domain.dao;

import br.com.estudojpa.domain.model.Produto;
import java.util.List;

public interface IProdutoDAO {

  List<Produto> listar();

  Produto salvar(Produto produto);

  List<Produto> buscar(String codigo);

  void atualizar(Produto produto);

  void remover(Produto produto);
}
