package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Marca;
import java.util.List;

public interface IMarcaDao {

  List<Marca> listar();

  Marca salvar(Marca marca);

  Marca buscar(Long id);

  void atualizar(Marca marca);

  void remover(Marca marca);
}
