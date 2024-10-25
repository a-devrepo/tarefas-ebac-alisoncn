package com.study.projeto.domain.dao.impl.produto;

import com.study.projeto.domain.dao.generic.ImplGenericDatabase1DAO;
import com.study.projeto.domain.model.relational.Produto;

public class ImplProdutoDatabase1DAO extends ImplGenericDatabase1DAO<Produto, Long> implements IProdutoDAO {
  public ImplProdutoDatabase1DAO() {
    super(Produto.class);
  }
}
