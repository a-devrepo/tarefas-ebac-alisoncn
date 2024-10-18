package com.study.projeto.services.impl;

import static org.junit.Assert.*;

import com.study.projeto.domain.builders.ClienteBuilder;
import com.study.projeto.domain.builders.EnderecoBuilder;
import com.study.projeto.domain.dao.impl.IClienteDAO;
import com.study.projeto.domain.dao.impl.ImplClienteDAO;
import com.study.projeto.domain.model.Cliente;
import com.study.projeto.domain.model.Endereco;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.util.GeradorCPF;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class IClienteServiceTest {

  IClienteDAO dao;
  IClienteService service;
  Cliente cliente;
  List<Cliente> clientes;

  public IClienteServiceTest() {
    dao = new ImplClienteDAO();
    service = new ImplClienteService(dao);
    clientes = new ArrayList<>();
  }

  @After
  public void end() throws DAOException {
    Collection<Cliente> lista = dao.listar();
    for (Cliente cliente : lista) {
      dao.excluir(cliente);
    }
  }

  @Test
  public void cadastrarClienteTest() throws DAOException {
    criarCliente();
    Cliente cadastrado = service.salvar(this.cliente);
    assertNotNull(cadastrado);
  }

  @Test
  public void buscarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    criarCliente();
    Cliente cadastrado = service.salvar(this.cliente);
    assertNotNull(cadastrado);

    Cliente clienteCadastrado = service.buscar(this.cliente.getId());

    assertNotNull(clienteCadastrado);

    assertEquals(this.cliente.getCpf(), clienteCadastrado.getCpf());
    assertEquals(this.cliente.getNome(), clienteCadastrado.getNome());
    assertEquals(this.cliente.getTelefone(), clienteCadastrado.getTelefone());
    assertEquals(
        this.cliente.getEndereco().getLogradouro(),
        clienteCadastrado.getEndereco().getLogradouro());
    assertEquals(
        this.cliente.getEndereco().getNumero(), clienteCadastrado.getEndereco().getNumero());
    assertEquals(
        this.cliente.getEndereco().getCidade(), clienteCadastrado.getEndereco().getCidade());
    assertEquals(
        this.cliente.getEndereco().getEstado(), clienteCadastrado.getEndereco().getEstado());
    assertEquals(this.cliente.getStatus(), clienteCadastrado.getStatus());
  }

  @Test
  public void alterarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    String nome = "Thiago Nathan da S.";
    String telefone = "(85)993654377";
    String endereco = "Rua dos inválidos";
    String numero = "818";
    String cidade = "Rio de Janeiro";
    String estado = "RJ";
    StatusCliente statusCliente = StatusCliente.INATIVO;

    criarCliente();
    service.salvar(this.cliente);

    Cliente clienteCadastrado = service.buscar(this.cliente.getId());

    clienteCadastrado.setNome(nome);
    clienteCadastrado.setTelefone(telefone);
    clienteCadastrado.getEndereco().setLogradouro(endereco);
    clienteCadastrado.getEndereco().setNumero(numero);
    clienteCadastrado.getEndereco().setCidade(cidade);
    clienteCadastrado.getEndereco().setEstado(estado);
    clienteCadastrado.setStatus(statusCliente);

    service.atualizar(clienteCadastrado);
    Cliente clienteAtualizado = service.buscar(this.cliente.getId());

    assertEquals(clienteAtualizado.getNome(), nome);
    assertEquals(clienteAtualizado.getTelefone(), telefone);
    assertEquals(clienteAtualizado.getEndereco().getLogradouro(), endereco);
    assertEquals(clienteAtualizado.getEndereco().getNumero(), numero);
    assertEquals(clienteAtualizado.getEndereco().getCidade(), cidade);
    assertEquals(clienteAtualizado.getEndereco().getEstado(), estado);
    assertEquals(clienteAtualizado.getStatus(), statusCliente);
  }

  @Test
  public void excluirClienteTest() throws DAOException {
    criarCliente();
    Cliente cadastrado = service.salvar(this.cliente);
    assertNotNull(cadastrado);

    service.excluir(this.cliente);

    var exception =
        assertThrows(
            RegistroNaoEncontradoException.class,
            () -> {
              service.buscar(this.cliente.getId());
            });

    assertEquals("Registro não encontrado", exception.getMessage());
  }

  @Test
  public void listarClientesTest() throws DAOException {
    Integer quantidadeEsperada = 2;
    criarListaClientes();
    for (Cliente cliente : clientes) {
      service.salvar(cliente);
    }
    Collection<Cliente> clientes = service.listar();

    assertEquals(quantidadeEsperada.intValue(), clientes.size());
  }

  public void criarCliente() {
    Endereco endereco = criarEndereco();
    this.cliente =
        ClienteBuilder.builder()
            .cpf(GeradorCPF.cpf(true))
            .nome("Thiago Nathan da Silva")
            .telefone("(85)99365-4377")
            .endereco(endereco)
            .status(StatusCliente.ATIVO)
            .build();
  }

  public Endereco criarEndereco() {
    return EnderecoBuilder.builder()
        .logradouro("Rua Coronel Matos Dourado")
        .numero("817")
        .cidade("Fortaleza")
        .estado("CE")
        .build();
  }

  public void criarListaClientes() {
    criarCliente();
    Endereco endereco =
        EnderecoBuilder.builder()
            .logradouro("Rua Coronel Matos Dourado")
            .numero("817")
            .cidade("Fortaleza")
            .estado("CE")
            .build();
    Cliente cliente2 =
        ClienteBuilder.builder()
            .nome("Fábio Kaique Gabriel Fogaça")
            .cpf(GeradorCPF.cpf(true))
            .endereco(endereco)
            .telefone("(36)98320-1276")
            .status(StatusCliente.ATIVO)
            .build();

    this.clientes.add(this.cliente);
    this.clientes.add(cliente2);
  }
}
