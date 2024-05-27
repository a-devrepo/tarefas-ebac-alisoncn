package com.javabackend.dao;

import com.javabackend.domain.Cliente;
import java.util.Collection;


/**
 *
 * @author alison
 */
public interface IClienteDao {
public Boolean cadastrar(Cliente cliente);

    public void excluir(String cpf);

    public void alterar(Cliente cliente);

    public Cliente consultar(String cpf);

    public Collection<Cliente> buscarTodos();

}
