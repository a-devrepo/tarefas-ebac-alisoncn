package com.study.projeto.domain.dao.impl.venda;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.Venda;
import com.study.projeto.exceptions.DAOException;

public interface IVendaDAO extends IGenericDAO<Venda,Long> {
    public Venda buscarVendaProdutos(Venda venda) throws DAOException;
}
