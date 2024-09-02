package com.sudy.projeto.services.impl;

import com.sudy.projeto.domain.dao.generic.ImplGenericDAO;
import com.sudy.projeto.domain.dao.impl.ImplVendaDAO;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.domain.model.Venda;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import com.sudy.projeto.services.generic.ImplGenericService;

public class ImplVendaService extends ImplGenericService<Venda, String> implements IVendaService {

  public ImplVendaService(ImplGenericDAO dao) {
    super(dao);
  }

  @Override
  public void cancelarVenda(Venda venda) throws DAOException {
    getDao().cancelarVenda(venda);
  }

  @Override
  public void concluirVenda(Venda venda) throws DAOException {
    getDao().concluirVenda(venda);
  }

  @Override
  public Boolean alterarQuantidadeProdutosVenda(Venda venda, String codigoProduto)
      throws RegistroNaoEncontradoException, DAOException {
    return getDao().alterarQuantidadeProdutosVenda(venda, codigoProduto);
  }

  @Override
  public Boolean adicionarNovoProdutoAVenda(Venda venda, Produto produto, Integer quantidade)
      throws DAOException {
    return getDao().adicionarNovoProdutoAVenda(venda, produto, quantidade);
  }

  @Override
  public Boolean removerProdutoDeVenda(Venda venda, Produto produto, Integer quantidade)
      throws DAOException {
    return getDao().removerProdutoDeVenda(venda, produto, quantidade);
  }

  @Override
  public Boolean removerTodosProdutosDeVenda(Venda venda) throws DAOException {
    return getDao().removerTodosProdutosDeVenda(venda);
  }

  private ImplVendaDAO getDao() {
    return (ImplVendaDAO) dao;
  }
}
