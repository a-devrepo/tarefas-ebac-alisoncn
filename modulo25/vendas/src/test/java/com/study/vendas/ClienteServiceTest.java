package com.study.vendas;

import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.dao.mocks.ImplClientDAOMock;
import com.study.vendas.domain.Cliente;
import com.study.vendas.exceptions.CodigoNaoEncontradoException;
import com.study.vendas.exceptions.DataBaseException;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import com.study.vendas.services.IClienteService;
import com.study.vendas.services.ImplClienteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClienteServiceTest {

    private IClienteService service;
    private IClienteDAO daoMock;
    Cliente cliente;

    public ClienteServiceTest(){
        daoMock = new ImplClientDAOMock();
        service = new ImplClienteService(daoMock);
    }
    @Before
    public void init(){
        cliente = new Cliente();
        cliente.setCpf("111.111.111-11");
        cliente.setCodigo(2367598130L);
        cliente.setNome("Salomão");
        cliente.setEndereco("Rua A");
        cliente.setNumero("10");
        cliente.setCidade("Rio de Janeiro");
        cliente.setEstado("RJ");
        cliente.setTelefone("(11)98435631");
    }

    @Test
    public void salvarclienteTest() throws DataBaseException, CodigoNaoEncontradoException {
        Cliente clienteSalvo = service.cadastrar(cliente);
        Assert.assertNotNull(clienteSalvo);
    }

    @Test
    public void pesquisarClienteTest() throws DataBaseException, CodigoNaoEncontradoException,
            RegistroNaoEncontradoException {
        Cliente clienteSalvo = service.cadastrar(cliente);
        Cliente clienteConsultado = service.buscarPorCpf(clienteSalvo.getCpf());
        Assert.assertNotNull(clienteConsultado);
  }

    @Test
    public void excluirClienteTest() throws DataBaseException, CodigoNaoEncontradoException,
            RegistroNaoEncontradoException {
        Cliente clienteSalvo = service.cadastrar(cliente);
        service.excluirPorCpf(clienteSalvo.getCpf());
    Assert.assertThrows(
        RegistroNaoEncontradoException.class, () -> service.buscarPorCpf(cliente.getCpf()));
  }

    @Test
    public void atualizarClienteTest() throws DataBaseException, CodigoNaoEncontradoException,
            RegistroNaoEncontradoException {
        Cliente clienteSalvo = service.cadastrar(cliente);
        clienteSalvo.setNome("Salomão II");
        clienteSalvo.setEndereco("Rua B");
        clienteSalvo.setNumero("200");
        clienteSalvo.setEstado("SP");
        clienteSalvo.setCidade("Capital");
        clienteSalvo.setTelefone("(11)98435631");

        service.atualizar(clienteSalvo);
        Cliente clienteAtualizado = service.buscarPorCpf(clienteSalvo.getCpf());
        Assert.assertEquals(clienteSalvo.getNome(),clienteAtualizado.getNome());
        Assert.assertEquals(clienteSalvo.getEndereco(),clienteAtualizado.getEndereco());
        Assert.assertEquals(clienteSalvo.getNumero(),clienteAtualizado.getNumero());
        Assert.assertEquals(clienteSalvo.getEstado(),clienteAtualizado.getEstado());
        Assert.assertEquals(clienteSalvo.getCidade(),clienteAtualizado.getCidade());
        Assert.assertEquals(clienteSalvo.getTelefone(),clienteAtualizado.getTelefone());
    }
}
