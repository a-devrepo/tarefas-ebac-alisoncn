package com.study.projeto.domain.dao.impl;

import com.study.projeto.domain.dao.generic.ImplGenericDAO;
import com.study.projeto.domain.model.Produto;

public class ImplProdutoDAO extends ImplGenericDAO<Produto, Long> implements IProdutoDAO {

  public ImplProdutoDAO() {
    super(Produto.class);
  }
}
