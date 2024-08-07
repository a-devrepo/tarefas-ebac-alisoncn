package com.study.vendas.dao;

import com.study.vendas.dao.mocks.ImplClientDAOMock;
import com.study.vendas.domain.Cliente;
import com.study.vendas.exceptions.CodigoNaoEncontradoException;
import com.study.vendas.exceptions.DataBaseException;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClienteDAOTest {

    IClienteDAO dao;
    Cliente cliente;

    public ClienteDAOTest() {
        this.dao = new ImplClientDAOMock();
    }

    @Before
    public void init() throws CodigoNaoEncontradoException, DataBaseException {
        cliente = new Cliente();
        cliente.setCodigo(2367598130L);
        cliente.setCpf("111.111.111-11");
        cliente.setNome("Salomao");
        cliente.setEndereco("Rua A");
        cliente.setNumero("10");
        cliente.setCidade("Rio de Janeiro");
        cliente.setEstado("RJ");
        cliente = dao.salvar(cliente);
    }

    @Test
    public void pesquisarClienteTest() throws RegistroNaoEncontradoException {
        Cliente clienteConsultado = dao.buscar(2367598130L);
        Assert.assertNotNull(clienteConsultado);
    }
}
