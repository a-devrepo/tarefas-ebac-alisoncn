package com.study.projeto.services.impl;

import com.study.projeto.domain.dao.impl.venda.ImplVendaDatabase1DAO;
import com.study.projeto.domain.model.ItemVenda;
import com.study.projeto.domain.model.Produto;
import com.study.projeto.domain.model.Venda;
import com.study.projeto.enums.StatusRegistro;
import com.study.projeto.exceptions.AlteracaoStatusVendaException;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.OperacaoNaoPermitidaException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.services.generic.ImplGenericService;

public class ImplVendaService extends ImplGenericService<Venda, Long> implements IVendaService {

  public ImplVendaService(ImplVendaDatabase1DAO dao) {
    super(dao);
  }

  @Override
  public Venda cancelarVenda(Venda venda) throws AlteracaoStatusVendaException, DAOException {
    venda.cancelarVenda();
    return dao.atualizar(venda);
  }

  @Override
  public Venda concluirVenda(Venda venda) throws AlteracaoStatusVendaException, DAOException {
    venda.concluirVenda();
    return dao.atualizar(venda);
  }

  @Override
  public Venda alterarQuantidadeProdutosItemVenda(Venda venda, String codigoProduto, Integer quantidade)
      throws RegistroNaoEncontradoException, OperacaoNaoPermitidaException, DAOException {
    venda = getDao().buscarVendaProdutos(venda);
    ItemVenda itemVenda =
        venda.getItens().stream()
            .filter(item -> item.getProduto().getCodigo().equalsIgnoreCase(codigoProduto))
            .findFirst()
            .orElseThrow(
                () ->
                    new RegistroNaoEncontradoException(
                        "O produto não se encontra na lista de itens"));
    venda.adicionarProduto(itemVenda.getProduto(), quantidade);
    return dao.atualizar(venda);
  }

  @Override
  public Venda adicionarNovoProdutoAVenda(Venda venda, Produto produto, Integer quantidade)
      throws OperacaoNaoPermitidaException, DAOException {
    venda.adicionarProduto(produto, quantidade);
    return dao.atualizar(venda);
  }

  @Override
  public Venda removerProdutoDeVenda(Venda venda, Produto produto, Integer quantidade)
      throws OperacaoNaoPermitidaException, DAOException {
    venda.removerProduto(produto, quantidade);
    return dao.atualizar(venda);
  }

  @Override
  public Venda removerTodosProdutosDeVenda(Venda venda) throws OperacaoNaoPermitidaException, DAOException {
    venda.getItens().forEach(itemVenda -> itemVenda.setStatusRegistro(StatusRegistro.INATIVO));
    venda = dao.atualizar(venda);
    venda.removerTodosItens();
    return venda;
  }

  public Venda buscarVendaItens(Venda venda) throws DAOException {
    return getDao().buscarVendaProdutos(venda);
  }

  private ImplVendaDatabase1DAO getDao() {
    return (ImplVendaDatabase1DAO) dao;
  }
}
