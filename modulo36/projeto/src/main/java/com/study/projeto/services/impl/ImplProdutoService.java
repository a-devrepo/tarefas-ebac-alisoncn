package com.study.projeto.services.impl;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.services.generic.ImplGenericService;

public class ImplProdutoService extends ImplGenericService<Produto, Long>
    implements IProdutoService {
  public ImplProdutoService(IGenericDAO dao) {
    super(dao);
  }
}
