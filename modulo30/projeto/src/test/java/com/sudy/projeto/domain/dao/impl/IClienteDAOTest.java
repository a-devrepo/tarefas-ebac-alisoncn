package com.sudy.projeto.domain.dao.impl;

import static org.junit.Assert.*;

import com.sudy.projeto.domain.builders.ClienteBuilder;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.enums.StatusCliente;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IClienteDAOTest {
    private IClienteDAO dao;

    public IClienteDAOTest(){
        dao = new ImplClienteDAO();
    }

    @After
    public void end() throws DAOException {
        Collection<Cliente> lista = dao.listar();
        for(Cliente cliente: lista){
            dao.excluir(cliente.getCpf());
        }
    }

    @Test
    public void cadastrarClienteTest() throws DAOException {
        Cliente cliente = ClienteBuilder
                .builder()
                .cpf("793237200308")
                .nome("Anderson Cauê")
                .endereco("Rua Joâo Penha")
                .numero("268")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .telefone("(21)3745-8377")
                .status(StatusCliente.ATIVO)
                .build();
        Boolean cadastrado = dao.salvar(cliente);
        Assert.assertTrue(cadastrado);
    }

    @Test
    public void pesquisarClienteTest() throws DAOException, RegistroNaoEncontradoException {
        String cpf = "793237200308";
        Cliente cliente = ClienteBuilder
                .builder()
                .cpf(cpf)
                .nome("Anderson Cauê")
                .endereco("Rua Joâo Penha")
                .numero("268")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .telefone("(21)3745-8377")
                .status(StatusCliente.ATIVO)
                .build();

        dao.salvar(cliente);
        Cliente clienteCadastrado = dao.buscar(cpf);
        Assert.assertNotNull(clienteCadastrado);
    }

    @Test
    public void alterarClienteTest() throws DAOException, RegistroNaoEncontradoException{
        String cpf = "793237200308";
        String nome = "Anderson Cauê Filho";
        String endereco = "Rua Davi Antonio Nogueira";
        String numero = "488";
        String cidade = "Nova Iguaçu";
        String estado = "RJ";
        String telefone = "(21) 99864-4088";
        StatusCliente statusCliente = StatusCliente.ATIVO;

        Cliente cliente = ClienteBuilder
                .builder()
                .cpf("793237200308")
                .nome("Anderson Cauê")
                .endereco("Rua Joâo Penha")
                .numero("268")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .telefone("(21)3745-8377")
                .status(StatusCliente.INATIVO)
                .build();

        dao.salvar(cliente);
        Cliente clienteCadastrado = dao.buscar(cpf);
        Assert.assertNotNull(clienteCadastrado);

        clienteCadastrado.setNome(nome);
        clienteCadastrado.setEndereco(endereco);
        clienteCadastrado.setNumero(numero);
        clienteCadastrado.setCidade(cidade);
        clienteCadastrado.setEstado(estado);
        clienteCadastrado.setTelefone(telefone);
        clienteCadastrado.setStatus(statusCliente);

        dao.atualizar(clienteCadastrado);
        clienteCadastrado = dao.buscar(cpf);

        Assert.assertEquals(nome,clienteCadastrado.getNome());
        Assert.assertEquals(endereco,clienteCadastrado.getEndereco());
        Assert.assertEquals(numero,clienteCadastrado.getNumero());
        Assert.assertEquals(cidade,clienteCadastrado.getCidade());
        Assert.assertEquals(estado,clienteCadastrado.getEstado());
        Assert.assertEquals(telefone,clienteCadastrado.getTelefone());
    }

    @Test
    public void excluirClienteTest() throws DAOException {
        Cliente cliente = ClienteBuilder
                .builder()
                .cpf("793237200308")
                .nome("Anderson Cauê")
                .endereco("Rua Joâo Penha")
                .numero("268")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .telefone("(21)3745-8377")
                .status(StatusCliente.ATIVO)
                .build();
        Boolean cadastrado = dao.salvar(cliente);
        Assert.assertTrue(cadastrado);

        dao.excluir(cliente.getCpf());

        RegistroNaoEncontradoException exception = assertThrows(RegistroNaoEncontradoException.class, () -> {
            dao.buscar(cliente.getCpf());
        });

        Assert.assertEquals("Registro não encontrado",exception.getMessage());
    }

    @Test
    public void listarTodosClientesTest() throws DAOException {
        Cliente cliente1 = ClienteBuilder
                .builder()
                .cpf("793237200308")
                .nome("Anderson Cauê")
                .endereco("Rua Joâo Penha")
                .numero("268")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .telefone("(21)3745-8377")
                .status(StatusCliente.ATIVO)
                .build();

        Cliente cliente2 = ClienteBuilder
                .builder()
                .cpf("63025228970")
                .nome("Isabelly Betina Oliveira")
                .endereco("Rua Tertuliano Sales")
                .numero("539")
                .cidade("Vitoria da Conquista")
                .estado("BA")
                .telefone("(77)99966-9001")
                .status(StatusCliente.ATIVO)
                .build();
        dao.salvar(cliente1);
        dao.salvar(cliente2);

        Collection<Cliente> clientes = dao.listar();

        Assert.assertEquals(2,clientes.size());
    }
}
