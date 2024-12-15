package com.nca.clienteservice.domain.repositories;

import com.nca.clienteservice.domain.model.Cliente;
import com.nca.clienteservice.exceptions.DAOException;
import com.nca.clienteservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CustomRepository {

    Page<Cliente> listar(Pageable pageable) throws DAOException;

    Cliente salvar(Cliente entity) throws DAOException;

    Cliente buscar(String code) throws RegistroNaoEncontradoException, DAOException;

    void excluir(String id) throws DAOException;

    Cliente atualizar(Cliente entity) throws DAOException;

    public Collection<Cliente> filtrarPor(String param, String value) throws DAOException;
}
