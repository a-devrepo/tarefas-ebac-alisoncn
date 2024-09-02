package com.sudy.projeto.services.impl;

import com.sudy.projeto.domain.dao.impl.IClienteDAO;
import com.sudy.projeto.domain.dao.impl.ImplClienteDAO;
import com.sudy.projeto.domain.factory.ClienteFactory;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.enums.StatusCliente;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

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
      dao.excluir(cliente.getCpf());
    }
  }

  @Test
  public void cadastrarClienteTest() throws DAOException {
    criarCliente();
    Boolean cadastrado = service.salvar(this.cliente);
    assertTrue(cadastrado);
  }

  @Test
  public void buscarClienteTest() throws DAOException, RegistroNaoEncontradoException {
    criarCliente();
    Boolean cadastrado = service.salvar(this.cliente);
    assertTrue(cadastrado);

    Cliente clienteCadastrado = service.buscar(this.cliente.getCpf());

    assertNotNull(clienteCadastrado);

    assertEquals(this.cliente.getCpf(),clienteCadastrado.getCpf());
    assertEquals(this.cliente.getNome(),clienteCadastrado.getNome());
    assertEquals(this.cliente.getTelefone(),clienteCadastrado.getTelefone());
    assertEquals(this.cliente.getEndereco(),clienteCadastrado.getEndereco());
    assertEquals(this.cliente.getNumero(),clienteCadastrado.getNumero());
    assertEquals(this.cliente.getCidade(),clienteCadastrado.getCidade());
    assertEquals(this.cliente.getEstado(),clienteCadastrado.getEstado());
    assertEquals(this.cliente.getStatus(),clienteCadastrado.getStatus());
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

    Cliente clienteCadastrado = service.buscar(this.cliente.getCpf());

    clienteCadastrado.setNome(nome);
    clienteCadastrado.setTelefone(telefone);
    clienteCadastrado.setEndereco(endereco);
    clienteCadastrado.setNumero(numero);
    clienteCadastrado.setCidade(cidade);
    clienteCadastrado.setEstado(estado);
    clienteCadastrado.setStatus(statusCliente);

    service.atualizar(clienteCadastrado);
    Cliente clienteAtualizado = service.buscar(this.cliente.getCpf());

    assertEquals(clienteAtualizado.getNome(),nome);
    assertEquals(clienteAtualizado.getTelefone(),telefone);
    assertEquals(clienteAtualizado.getEndereco(),endereco);
    assertEquals(clienteAtualizado.getNumero(),numero);
    assertEquals(clienteAtualizado.getCidade(),cidade);
    assertEquals(clienteAtualizado.getEstado(),estado);
    assertEquals(clienteAtualizado.getStatus(),statusCliente);
  }

  @Test
  public void excluirClienteTest() throws DAOException {
    criarCliente();
    Boolean cadastrado = service.salvar(this.cliente);
    assertTrue(cadastrado);

    service.excluir(this.cliente.getCpf());

    var exception = assertThrows(RegistroNaoEncontradoException.class, () -> {
      service.buscar(this.cliente.getCpf());
    });

    assertEquals("Registro não encontrado",exception.getMessage());
  }

  @Test
  public void listarClientesTest() throws DAOException {
    Integer quantidadeEsperada = 2;
    criarListaClientes();
    for(Cliente cliente: clientes){
      service.salvar(cliente);
    }
    Collection<Cliente> clientes = service.listar();

    assertEquals(quantidadeEsperada.intValue(),clientes.size());
  }

  public void criarCliente() {
    this.cliente = (Cliente)
            new ClienteFactory()
                    .create(
                            "743.336.399-80",
                            "Thiago Nathan da Silva",
                            "(85)99365-4377",
                            "Rua Coronel Matos Dourado",
                            "817",
                            "Fortaleza",
                            "CE",
                            StatusCliente.ATIVO);
  }

  public void criarListaClientes() {
    this.cliente = (Cliente)
            new ClienteFactory()
                    .create(
                            "367.947.439-30",
                            "Fábio Kaique Gabriel Fogaça",
                            "(19) 98845-5690",
                            "Rua Macaúba",
                            "972",
                            "Limeira",
                            "SP",
                            StatusCliente.ATIVO);
    Cliente cliente2 = (Cliente)
            new ClienteFactory()
                    .create(
                            "743.336.399-80",
                            "Thiago Nathan da Silva",
                            "(85)99365-4377",
                            "Rua Coronel Matos Dourado",
                            "817",
                            "Fortaleza",
                            "CE",
                            StatusCliente.ATIVO);

    this.clientes.add(cliente);
    this.clientes.add(cliente2);
  }
}
