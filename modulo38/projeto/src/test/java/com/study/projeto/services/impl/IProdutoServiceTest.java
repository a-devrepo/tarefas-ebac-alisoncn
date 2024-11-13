package com.study.projeto.services.impl;

import com.study.projeto.domain.builders.ProdutoBuilder;
import com.study.projeto.domain.dao.impl.produto.IProdutoDAO;
import com.study.projeto.domain.dao.impl.produto.ImplProdutoDAO;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.util.GeradorCodigo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IProdutoServiceTest {

  private IProdutoService service;
  private IProdutoDAO dao;
  private List<Produto> produtos;
  private Produto produto;

  public IProdutoServiceTest() {
    this.dao = new ImplProdutoDAO();
    this.service = new ImplProdutoService(dao);
    this.produtos = new ArrayList<>();
  }

  @After
  public void end() throws DAOException {
    Collection<Produto> lista = dao.listar();
    for (Produto produto : lista) {
      dao.excluir(produto);
    }
  }

  @Test
  public void cadastrarProdutoTest() throws DAOException, RegistroNaoEncontradoException {
    criarProduto();
    Produto cadastrado = service.salvar(this.produto);
    Assert.assertNotNull(cadastrado);

    Produto produtoCadastrado = service.buscar(this.produto.getId());
    Assert.assertNotNull(produtoCadastrado);
  }

  @Test
  public void buscarProdutoTest() throws DAOException, RegistroNaoEncontradoException {
    criarProduto();
    Produto cadastrado = service.salvar(this.produto);
    Assert.assertNotNull(cadastrado);

    Produto produtoCadastrado = service.buscar(this.produto.getId());
    Assert.assertNotNull(produtoCadastrado);

    Assert.assertEquals(this.produto.getId(), produtoCadastrado.getId());
    Assert.assertEquals(this.produto.getCodigo(), produtoCadastrado.getCodigo());
    Assert.assertEquals(this.produto.getNome(), produtoCadastrado.getNome());
    Assert.assertEquals(this.produto.getDescricao(), produtoCadastrado.getDescricao());
    Assert.assertEquals(this.produto.getFabricante(), produtoCadastrado.getFabricante());
    Assert.assertEquals(this.produto.getValor(), produtoCadastrado.getValor());
  }

  @Test
  public void alterarProdutoTest() throws DAOException, RegistroNaoEncontradoException {

    String nome = "Notebook para jogos";
    String descricao = "Notebook para jogos de alta resolução";
    String fabricante = "ACER Inc";
    BigDecimal valor = new BigDecimal("8000.00");

    criarProduto();
    service.salvar(this.produto);

    Produto produtoCadastrado = service.buscar(this.produto.getId());

    produtoCadastrado.setNome(nome);
    produtoCadastrado.setDescricao(descricao);
    produtoCadastrado.setFabricante(fabricante);
    produtoCadastrado.setValor(valor);

    service.atualizar(produtoCadastrado);

    Produto produtoAtualizado = service.buscar(produtoCadastrado.getId());

    Assert.assertEquals(nome, produtoAtualizado.getNome());
    Assert.assertEquals(descricao, produtoAtualizado.getDescricao());
    Assert.assertEquals(fabricante, produtoAtualizado.getFabricante());
    Assert.assertEquals(valor, produtoCadastrado.getValor());
  }

  @Test
  public void excluirProdutoTest() throws DAOException, RegistroNaoEncontradoException {
    criarProduto();
    service.salvar(this.produto);
    Produto produtoCadastrado = service.buscar(this.produto.getId());

    service.excluir(produtoCadastrado);

    var exception =
        Assert.assertThrows(
            RegistroNaoEncontradoException.class,
            () -> {
              service.buscar(produtoCadastrado.getId());
            });

    Assert.assertEquals("Registro não encontrado", exception.getMessage());
  }

  @Test
  public void listarProdutosTest() throws DAOException {
    criarLista();
    for (Produto produto : produtos) {
      service.salvar(produto);
    }
    Collection<Produto> produtos = service.listar();

    Assert.assertEquals(2, produtos.size());
  }

  private void criarProduto() {
    this.produto =
        ProdutoBuilder.builder()
            .codigo(GeradorCodigo.codigo())
            .nome("Notebook Gamer")
            .descricao("Notebook com alto desempenho")
            .fabricante("ACER")
            .valor(new BigDecimal("6000.00"))
            .build();
  }

  private void criarLista() {
    criarProduto();
    Produto produto2 =
        ProdutoBuilder.builder()
            .codigo(GeradorCodigo.codigo())
            .nome("Monitor Samsung")
            .descricao("Monitor Wide 25")
            .fabricante("SAMSUNG")
            .valor(new BigDecimal("3000.00"))
            .build();
    this.produtos.add(this.produto);
    this.produtos.add(produto2);
  }
}
