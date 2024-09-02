package com.sudy.projeto.services.impl;

import com.sudy.projeto.domain.dao.generic.IGenericDAO;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.services.generic.ImplGenericService;

public class ImplProdutoService extends ImplGenericService<Produto,Long> implements IProdutoService  {
    public ImplProdutoService(IGenericDAO dao) {
        super(dao);
    }
}
