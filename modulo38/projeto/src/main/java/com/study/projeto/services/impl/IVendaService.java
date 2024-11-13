package com.study.projeto.services.impl;

import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.domain.model.relational.Venda;
import com.study.projeto.exceptions.AlteracaoStatusVendaException;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.OperacaoNaoPermitidaException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.services.generic.IGenericService;

import javax.ejb.Local;

@Local
public interface IVendaService extends IGenericService<Venda, Long> {
    public Venda cancelarVenda(Venda venda) throws DAOException, AlteracaoStatusVendaException;

    public Venda concluirVenda(Venda venda) throws DAOException, AlteracaoStatusVendaException;

    public Venda alterarQuantidadeProdutosItemVenda(
            Venda venda, String codigoProduto, Integer quantidade)
            throws RegistroNaoEncontradoException, OperacaoNaoPermitidaException, DAOException;

    public Venda adicionarNovoProdutoAVenda(Venda venda, Produto produto, Integer quantidade)
            throws DAOException, OperacaoNaoPermitidaException;

    public Venda removerProdutoDeVenda(Venda venda, Produto produto, Integer quantidade)
            throws DAOException, OperacaoNaoPermitidaException;

    public Venda removerTodosProdutosDeVenda(Venda venda)
            throws DAOException, OperacaoNaoPermitidaException;

    public Venda buscarVendaItens(Venda venda) throws RegistroNaoEncontradoException, OperacaoNaoPermitidaException, DAOException;
}
