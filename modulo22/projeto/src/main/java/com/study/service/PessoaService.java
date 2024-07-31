package com.study.service;

import com.study.dao.GenericDao;
import com.study.domain.Pessoa;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaService extends GenericService<Pessoa, Long> implements IPessoaService {

  public PessoaService(GenericDao<Pessoa, Long> dao) {
    super(dao);
  }

  public List<Pessoa> listarPessoaPorGenero(String sigla) throws RuntimeException{
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
