package com.study.projeto.domain.dao.impl.produto;

import com.study.projeto.domain.dao.generic.ImplGenericDatabase2DAO;
import com.study.projeto.domain.model.Produto;

public class ImplProdutoDatabase2DAO extends ImplGenericDatabase2DAO<Produto, Long> implements IProdutoDAO {
  public ImplProdutoDatabase2DAO() {
    super(Produto.class);
  }
}
