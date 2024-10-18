package com.study.projeto.domain.dao.impl;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.Produto;
import com.study.projeto.domain.model.Venda;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.OperacaoNaoPermitidaException;
import com.study.projeto.exceptions.RegistroDuplicadoException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;

public interface IVendaDAO extends IGenericDAO<Venda,Long> {
    public Venda buscarVendaProdutos(Venda venda) throws DAOException;
}
