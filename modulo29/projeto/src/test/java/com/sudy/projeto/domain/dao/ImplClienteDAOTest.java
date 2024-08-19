package com.sudy.projeto.domain.dao;

import static org.junit.Assert.*;

import com.sudy.projeto.domain.dao.impl.IClienteDAO;
import com.sudy.projeto.domain.dao.impl.ImplClienteDAO;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.exceptions.CodigoNaoEncontradoException;
import com.sudy.projeto.exceptions.DataBaseException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImplClienteDAOTest {


  private IClienteDAO dao;

  @Before
  public void setup() {
    dao = new ImplClienteDAO();
  }

  @Test
  public void cadastrarClienteTest()
      throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Cliente cliente = new Cliente();
    cliente.setCpf("11111111111");
    cliente.setNome("Fulano");
    cliente.setSobreNnome("DeTal");
    cliente.setTelefone("2197321093");
    cliente.setEndereco("Rua A, casa 2, Vila da Rosa, Rio de Janeiro, RJ");

    Long idCliente = (long) dao.salvar(cliente);

    assertTrue(idCliente !=0L);

    cliente = dao.buscar(idCliente);

    Assert.assertNotNull(cliente);

    dao.excluir(idCliente);

  }

  @Test
  public void listarClientesTest()
          throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Cliente cliente1 = new Cliente();
    cliente1.setCpf("11111111111");
    cliente1.setNome("Fulano");
    cliente1.setSobreNnome("DeTal");
    cliente1.setTelefone("2197321093");
    cliente1.setEndereco("Rua A, casa 2, Vila da Rosa, Rio de Janeiro, RJ");

    Cliente cliente2 = new Cliente();
    cliente2.setCpf("11111111111");
    cliente2.setNome("Ronaldo");
    cliente2.setSobreNnome("O Bruxo");
    cliente2.setTelefone("2194356785");
    cliente2.setEndereco("Rua B, casa 7, Bairro Z, Porto Alegre, RS");

    Long idCliente1 = (long) dao.salvar(cliente1);
    Long idCliente2 = (long) dao.salvar(cliente2);

    List<Cliente> clientes = dao.listar();

    Assert.assertEquals(2,clientes.size());
    
    dao.excluir(idCliente1);
    dao.excluir(idCliente2);
  }


  @Test
  public void buscarClienteTest()
          throws SQLException, CodigoNaoEncontradoException
          , DataBaseException, IllegalAccessException, RegistroNaoEncontradoException {
    Cliente cliente = new Cliente();
    cliente.setCpf("11111111111");
    cliente.setNome("Ronaldo");
    cliente.setSobreNnome("O Bruxo");
    cliente.setTelefone("2194356785");
    cliente.setEndereco("Rua B, casa 7, Bairro Z, Porto Alegre, RS");

    Long idCliente =(long) dao.salvar(cliente);

    cliente = dao.buscar(idCliente);
    assertNotNull(cliente);
    dao.excluir(idCliente);
  }


  @Test
  public void atualizarClienteTest()
          throws SQLException, CodigoNaoEncontradoException
          , DataBaseException, IllegalAccessException, RegistroNaoEncontradoException {
    Cliente cliente = new Cliente();
    cliente.setCpf("11111111111");
    cliente.setNome("Ronaldo");
    cliente.setSobreNnome("O Bruxo");
    cliente.setTelefone("2194356785");
    cliente.setEndereco("Rua B, casa 7, Bairro Z, Porto Alegre, RS");

    Long idCliente =(long) dao.salvar(cliente);

    Cliente clienteAtualizado = dao.buscar(idCliente);

    clienteAtualizado.setNome("Ronaldinho");
    clienteAtualizado.setSobreNnome("Dibrador");
    clienteAtualizado.setTelefone("2198324576");
    clienteAtualizado.setEndereco("Rua do Dibre, Bairro B, São Paulo, SP");

    dao.atualizar(clienteAtualizado);

    clienteAtualizado = dao.buscar(idCliente);

    assertEquals("Ronaldinho",clienteAtualizado.getNome());
    assertEquals("Dibrador",clienteAtualizado.getSobreNnome());
    assertEquals("2198324576",clienteAtualizado.getTelefone());
    assertEquals("Rua do Dibre, Bairro B, São Paulo, SP",clienteAtualizado.getEndereco());

    dao.excluir(idCliente);
  }

  @Test
  public void excluirClienteTest()
          throws SQLException, CodigoNaoEncontradoException
          , DataBaseException, IllegalAccessException, RegistroNaoEncontradoException {
    Cliente cliente = new Cliente();
    cliente.setCpf("11111111111");
    cliente.setNome("Ronaldo");
    cliente.setSobreNnome("O Bruxo");
    cliente.setTelefone("2194356785");
    cliente.setEndereco("Rua B, casa 7, Bairro Z, Porto Alegre, RS");

    Long idCliente =(long) dao.salvar(cliente);

    dao.excluir(idCliente);

    Exception e = assertThrows(RegistroNaoEncontradoException.class, () -> dao.buscar(idCliente));
    
    assertEquals("Registro não encontrado",e.getMessage());
  }
}

