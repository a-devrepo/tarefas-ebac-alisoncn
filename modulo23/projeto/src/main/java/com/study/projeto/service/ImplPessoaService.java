package com.study.projeto.service;

import com.study.projeto.domain.dao.ImplGenericDAO;
import com.study.projeto.domain.model.Pessoa;
import java.util.List;
import java.util.stream.Collectors;

public class ImplPessoaService extends ImplGenericService<Pessoa, Long> implements IPessoaService {

  public ImplPessoaService(ImplGenericDAO<Pessoa, Long> dao) {
    super(dao);
  }

  public List<Pessoa> listarPessoaPorGenero(String sigla) throws RuntimeException {
    List<Pessoa> pessoas =
        dao.listar().stream()
            .filter((pessoa) -> pessoa.getGenero().getSigla().equals(sigla.toUpperCase()))
            .collect(Collectors.toList());

    if (pessoas.isEmpty()) {
      throw new RuntimeException("Não há dados para exibir");
    }
    return pessoas;
  }
}
