package com.nca.vendaservice.domain.repositories;

import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CustomRepository {
    Page<Venda> listar(Pageable pageable) throws DAOException;

    Venda salvar(Venda entity) throws DAOException;

    Venda buscar(String code) throws RegistroNaoEncontradoException, DAOException;

    void excluir(String id) throws DAOException;

    Venda atualizar(Venda entity) throws DAOException;

    public Collection<Venda> filtrarPor(String param, String value) throws DAOException;
}
