package com.study.projeto.domain.dao.impl.produto;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.exceptions.DAOException;

import java.util.Collection;

public interface IProdutoDAO extends IGenericDAO<Produto,Long> {
    public Collection<Produto> findBy(String param, String value) throws DAOException;
}
