package com.study.projeto.domain.dao.impl;

import static org.junit.Assert.*;

import com.study.projeto.domain.builders.ProdutoBuilder;
import com.study.projeto.domain.dao.impl.produto.IProdutoDAO;
import com.study.projeto.domain.dao.impl.produto.ImplProdutoDatabase3DAO;
import com.study.projeto.domain.model.Produto;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.util.GeradorCodigo;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IProdutoDatabase3DAOTest {

  private IProdutoDAO dao;

  public IProdutoDatabase3DAOTest() {
    dao = new ImplProdutoDatabase3DAO();
  }

  @After
  public void end() throws DAOException {
    Collection<Produto> lista = dao.listar();
    for (Produto produto : lista) {
      dao.excluir(produto);
    }
  }

  @Test
  public void cadastrarProdutoTest() throws DAOException {
    Produto produto = criarProduto();
    Produto cadastrado = dao.salvar(produto);
    Assert.assertNotNull(cadastrado);
  }

  @Test
  public void pesquisarProdutoTest() throws DAOException, RegistroNaoEncontradoException {
    Produto produto = criarProduto();
    dao.salvar(produto);
    Produto produtoCadastrado = dao.buscar(produto.getId());
    Assert.assertNotNull(produtoCadastrado);
  }

  @Test
  public void alterarProdutoTest() throws DAOException, RegistroNaoEncontradoException {

    String nome = "Notebook Gamer Acer";
    String descricao = "Notebook ideal para jogos que demandam alta performance";
    String fabricante = "ACER INC";
    BigDecimal valor = new BigDecimal("8000.00");
    Produto produto = criarProduto();

    dao.salvar(produto);
    Produto produtoCadastrado = dao.buscar(produto.getId());

    produtoCadastrado.setNome(nome);
    produtoCadastrado.setDescricao(descricao);
    produtoCadastrado.setValor(valor);
    produtoCadastrado.setFabricante(fabricante);
    dao.atualizar(produtoCadastrado);

    produtoCadastrado = dao.buscar(produto.getId());

    Assert.assertEquals(nome, produtoCadastrado.getNome());
    Assert.assertEquals(descricao, produtoCadastrado.getDescricao());
    Assert.assertEquals(valor, produtoCadastrado.getValor());
  }

  @Test
  public void excluirProdutoTest() throws DAOException {
    Produto produto = criarProduto();

    dao.salvar(produto);
    dao.excluir(produto);

    RegistroNaoEncontradoException exception =
        assertThrows(
            RegistroNaoEncontradoException.class,
            () -> {
              dao.buscar(produto.getId());
            });

    Assert.assertEquals("Registro não encontrado", exception.getMessage());
  }

  @Test
  public void listarTodosProdutosTest() throws DAOException {
    Produto produto1 = criarProduto();
    Produto produto2 =
        ProdutoBuilder.builder()
            .codigo(GeradorCodigo.codigo())
            .nome("Monitor Samsung")
            .descricao("Monitor Wide 25 de alta resolução")
            .fabricante("SAMSUNG")
            .valor(new BigDecimal("3000.00"))
            .build();
    dao.salvar(produto1);
    dao.salvar(produto2);

    Collection<Produto> produtos = dao.listar();

    Assert.assertEquals(2, produtos.size());
  }

  private Produto criarProduto() {
      return ProdutoBuilder.builder()
              .codigo(GeradorCodigo.codigo())
              .nome("Notebook Gamer")
              .fabricante("ACER")
              .descricao("Notebook da marca Acer")
              .valor(new BigDecimal("6000.00"))
              .build();
  }
}
