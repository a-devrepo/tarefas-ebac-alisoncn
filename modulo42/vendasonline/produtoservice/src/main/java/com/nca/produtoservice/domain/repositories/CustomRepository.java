package com.nca.produtoservice.domain.repositories;

import com.nca.produtoservice.domain.model.Produto;
import com.nca.produtoservice.exceptions.DAOException;
import com.nca.produtoservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CustomRepository {
    Page<Produto> listar(Pageable pageable) throws DAOException;

    Produto salvar(Produto entity) throws DAOException;

    Produto buscar(Long code) throws RegistroNaoEncontradoException, DAOException;

    void excluir(Long id) throws DAOException;

    Produto atualizar(Produto entity) throws DAOException;

    public Collection<Produto> filtrarPor(String param, String value) throws DAOException;
}
