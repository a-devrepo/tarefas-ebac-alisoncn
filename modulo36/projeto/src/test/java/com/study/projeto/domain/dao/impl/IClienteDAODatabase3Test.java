package com.study.projeto.domain.dao.impl;

import static org.junit.Assert.*;

import com.study.projeto.domain.builders.ClienteBuilder;
import com.study.projeto.domain.builders.EnderecoBuilder;
import com.study.projeto.domain.dao.impl.cliente.IClienteDAO;
import com.study.projeto.domain.dao.impl.cliente.ImplClienteDatabase3DAO;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.domain.model.relational.Endereco;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.util.GeradorCPF;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IClienteDAODatabase3Test {
  private IClienteDAO dao;

  public IClienteDAODatabase3Test() {
    dao = new ImplClienteDatabase3DAO();
  }

  @After
  public void end() throws DAOException {
    Collection<Cliente> lista = dao.listar();
    for (Cliente cliente : lista) {
      dao.excluir(cliente);
    }
  }

  private Cliente criarCliente() {
    Endereco endereco = criarEndereco();
    String cpf = GeradorCPF.cpf(true);
    Cliente cliente =
        ClienteBuilder.builder()
            .cpf(cpf)
            .nome("Anderson Cauê")
            .endereco(endereco)
            .telefone("(21)3745-8377")
            .status(StatusCliente.ATIVO)
            .build();
    return cliente;
  }

  private Endereco criarEndereco() {
    return EnderecoBuilder.builder()
        .logradouro("Rua João Penha")
        .numero("268")
        .cidade("Rio de Janeiro")
        .estado("RJ")
        .build();
  }

  @Test
  public void cadastrarClienteTest() throws DAOException {
    Cliente cliente = criarCliente();
    Cliente cadastrado = dao.salvar(cliente);
    Assert.assertNotNull(cadastrado);
  }

  @Test
  public void pesquisarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    Cliente cliente = criarCliente();
    dao.salvar(cliente);
    Cliente clienteCadastrado = dao.buscar(cliente.getId());
    Assert.assertNotNull(clienteCadastrado);
  }

  @Test
  public void alterarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    String nome = "Anderson Cauê Filho";
    String endereco = "Rua Davi Antonio Nogueira";
    String numero = "488";
    String cidade = "Nova Iguaçu";
    String estado = "RJ";
    String telefone = "(21) 99864-4088";


    Cliente cliente = criarCliente();

    dao.salvar(cliente);
    Cliente clienteCadastrado = dao.buscar(cliente.getId());
    Assert.assertNotNull(clienteCadastrado);

    clienteCadastrado.setNome(nome);
    clienteCadastrado.getEndereco().setLogradouro(endereco);
    clienteCadastrado.getEndereco().setNumero(numero);
    clienteCadastrado.getEndereco().setCidade(cidade);
    clienteCadastrado.getEndereco().setEstado(estado);
    clienteCadastrado.setTelefone(telefone);
    clienteCadastrado.setStatus(StatusCliente.INATIVO);

    dao.atualizar(clienteCadastrado);

    Assert.assertEquals(nome, clienteCadastrado.getNome());
    Assert.assertEquals(endereco, clienteCadastrado.getEndereco().getLogradouro());
    Assert.assertEquals(numero, clienteCadastrado.getEndereco().getNumero());
    Assert.assertEquals(cidade, clienteCadastrado.getEndereco().getCidade());
    Assert.assertEquals(estado, clienteCadastrado.getEndereco().getEstado());
    Assert.assertEquals(telefone, clienteCadastrado.getTelefone());
    Assert.assertEquals(StatusCliente.INATIVO, clienteCadastrado.getStatus());
  }

  @Test
  public void excluirClienteTest() throws DAOException {
    Cliente cliente = criarCliente();
    Cliente cadastrado = dao.salvar(cliente);
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
    Cliente cliente1 = criarCliente();

    Endereco endereco =
        EnderecoBuilder.builder()
            .logradouro("Rua Tertuliano Sales")
            .numero("539")
            .cidade("Vitoria da Conquista")
            .estado("BA")
            .build();
    Cliente cliente2 =
        ClienteBuilder.builder()
            .cpf(GeradorCPF.cpf(true))
            .nome("Isabelly Betina Oliveira")
            .endereco(endereco)
            .telefone("(77)99966-9001")
            .status(StatusCliente.ATIVO)
            .build();
    dao.salvar(cliente1);
    dao.salvar(cliente2);

    Collection<Cliente> clientes = dao.listar();

    Assert.assertEquals(2, clientes.size());
  }
}
