package com.study.projeto.services.impl;

import com.study.projeto.domain.dao.impl.cliente.IClienteDAO;
import com.study.projeto.domain.dao.impl.produto.IProdutoDAO;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.services.generic.ImplGenericService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ImplProdutoService extends ImplGenericService<Produto, Long>
        implements IProdutoService {


    @Inject
    public ImplProdutoService(IProdutoDAO dao) {
        super(dao);
    }

    @Override
    public Collection<Produto> filtrarProdutosPor(String param, String value) throws DAOException {
        Collection<Produto> produtos =((IProdutoDAO)dao).findBy(param, value);
        return produtos;
    }
}
