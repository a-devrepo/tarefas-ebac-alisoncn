package com.study.projeto.domain.dao.impl.produto;

import com.study.projeto.domain.dao.generic.ImplGenericDatabase3DAO;
import com.study.projeto.domain.model.Produto;

public class ImplProdutoDatabase3DAO extends ImplGenericDatabase3DAO<Produto, Long> implements IProdutoDAO {
  public ImplProdutoDatabase3DAO() {
    super(Produto.class);
  }
}
