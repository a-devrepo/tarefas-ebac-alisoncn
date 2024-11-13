package com.study.projeto.services.impl;

import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.services.generic.IGenericService;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface IProdutoService extends IGenericService<Produto, Long> {
    public Collection<Produto> filtrarProdutosPor(String param, String value) throws DAOException;
}
