package com.sudy.projeto.domain.dao.impl;

import static org.junit.Assert.*;

import com.sudy.projeto.domain.builders.ProdutoBuilder;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IProdutoDAOTest {
  private IProdutoDAO dao;

  public IProdutoDAOTest() {
    dao = new ImplProdutoDAO();
  }

  @After
  public void end() throws DAOException {
    Collection<Produto> lista = dao.listar();
    for (Produto produto : lista) {
      dao.excluir(produto.getId());
    }
  }

  @Test
  public void cadastrarProdutoTest() throws DAOException {
    Produto produto =
        ProdutoBuilder.builder()
            .codigo("83021")
            .nome("Notebook Gamer")
            .fabricante("ACER")
            .descricao("Notebook da marca Acer")
            .valor(new BigDecimal("6000.00"))
            .build();

    Boolean cadastrado = dao.salvar(produto);
    Assert.assertTrue(cadastrado);
  }

  @Test
  public void pesquisarProdutoTest() throws DAOException, RegistroNaoEncontradoException {

    Produto produto =
        ProdutoBuilder.builder()
            .codigo("83021")
            .nome("Notebook Gamer")
            .descricao("Notebook da marca Acer")
            .fabricante("ACER")
            .valor(new BigDecimal("6000.00"))
            .build();

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
    Produto produto =
        ProdutoBuilder.builder()
            .codigo("83021")
            .nome("Notebook Gamer")
            .descricao("Notebook da marca Acer")
            .fabricante("ACER")
            .valor(new BigDecimal("6000.00"))
            .build();

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
    Produto produto =
        ProdutoBuilder.builder()
            .codigo("83021")
            .nome("Notebook Gamer")
            .descricao("Notebook da marca Acer")
            .fabricante("ACER")
            .valor(new BigDecimal("6000.00"))
            .build();

    dao.salvar(produto);
    dao.excluir(produto.getId());

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
    Produto produto1 =
        ProdutoBuilder.builder()
            .codigo("83021")
            .nome("Notebook Gamer")
            .descricao("Notebook da marca Acer")
            .fabricante("ACER")
            .valor(new BigDecimal("6000.00"))
            .build();

    Produto produto2 =
        ProdutoBuilder.builder()
            .codigo("47287")
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
}
