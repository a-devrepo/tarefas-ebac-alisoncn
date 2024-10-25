package com.study.projeto.domain.dao.impl;

import static org.junit.Assert.*;

import com.study.projeto.domain.builders.ClienteMDBuilder;
import com.study.projeto.domain.dao.impl.cliente.IClienteMDDAO;
import com.study.projeto.domain.dao.impl.cliente.ImplClienteMDDAO;
import com.study.projeto.domain.model.nonrelational.ClienteMD;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.util.GeradorCPF;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IClienteMDDAOTest {
  private IClienteMDDAO dao;

  public IClienteMDDAOTest() {
    dao = new ImplClienteMDDAO();
  }

  @After
  public void end() throws DAOException {
    Collection<ClienteMD> lista = dao.listar();
    for (ClienteMD cliente : lista) {
      dao.excluir(cliente);
    }
  }

  private ClienteMD criarCliente() {

    String cpf = GeradorCPF.cpf(true);
    ClienteMD cliente =
        ClienteMDBuilder.builder()
            .cpf(cpf)
            .nome("Anderson Cauê")
            .telefone("(21)3745-8377")
            .build();
    return cliente;
  }

  @Test
  public void cadastrarClienteTest() throws DAOException {
    ClienteMD cliente = criarCliente();
    ClienteMD cadastrado = dao.salvar(cliente);
    Assert.assertNotNull(cadastrado);
  }

  @Test
  public void pesquisarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    ClienteMD cliente = criarCliente();
    dao.salvar(cliente);
    ClienteMD clienteCadastrado = dao.buscar(cliente.getId());
    Assert.assertNotNull(clienteCadastrado);
  }

  @Test
  public void alterarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    String nome = "Anderson Cauê Filho";
    String telefone = "(21) 99864-4088";

    ClienteMD cliente = criarCliente();

    dao.salvar(cliente);
    ClienteMD clienteCadastrado = dao.buscar(cliente.getId());
    Assert.assertNotNull(clienteCadastrado);

    clienteCadastrado.setNome(nome);
    clienteCadastrado.setTelefone(telefone);

    dao.atualizar(clienteCadastrado);

    ClienteMD atualizado = dao.buscar(clienteCadastrado.getId());

    Assert.assertEquals(nome, atualizado.getNome());
    Assert.assertEquals(telefone, atualizado.getTelefone());
  }

  @Test
  public void excluirClienteTest() throws DAOException {
    ClienteMD cliente = criarCliente();
    ClienteMD cadastrado = dao.salvar(cliente);
    Assert.assertNotNull(cadastrado);

    dao.excluir(cadastrado);

    RegistroNaoEncontradoException exception =
        assertThrows(
                RegistroNaoEncontradoException.class,
            () -> {
              dao.buscar(cliente.getId());
            });

    Assert.assertEquals("Registro não encontrado", exception.getMessage());
  }

  @Test
  public void listarTodosClientesTest() throws DAOException {
    ClienteMD cliente1 = criarCliente();
    ClienteMD cliente2 =
        ClienteMDBuilder.builder()
            .cpf(GeradorCPF.cpf(true))
            .nome("Isabelly Betina Oliveira")
            .telefone("(77)99966-9001")
            .status(StatusCliente.ATIVO)
            .build();
    dao.salvar(cliente1);
    dao.salvar(cliente2);

    Collection<ClienteMD> clientes = dao.listar();

    Assert.assertEquals(2, clientes.size());
  }
}
