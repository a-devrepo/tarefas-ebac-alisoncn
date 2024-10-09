package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Carro;
import java.util.List;

public interface ICarroDao {
  List<Carro> listar();

  Carro salvar(Carro carro);

  Carro buscar(Long id);

  void atualizar(Carro carro);

  void remover(Carro carro);
}
