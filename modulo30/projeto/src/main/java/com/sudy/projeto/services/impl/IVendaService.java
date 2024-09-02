package com.sudy.projeto.services.impl;

import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.domain.model.Venda;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import com.sudy.projeto.services.generic.IGenericService;

public interface IVendaService extends IGenericService<Venda,String> {
    public void cancelarVenda(Venda venda) throws DAOException;
    public void concluirVenda(Venda venda) throws DAOException;
    public Boolean alterarQuantidadeProdutosVenda(Venda venda, String codigoProduto)
            throws RegistroNaoEncontradoException, DAOException;
    public Boolean adicionarNovoProdutoAVenda(Venda venda, Produto produto, Integer quantidade) throws DAOException;
    public Boolean removerProdutoDeVenda(Venda venda, Produto produto, Integer quantidade) throws DAOException;
    public Boolean removerTodosProdutosDeVenda(Venda venda) throws DAOException;
}
