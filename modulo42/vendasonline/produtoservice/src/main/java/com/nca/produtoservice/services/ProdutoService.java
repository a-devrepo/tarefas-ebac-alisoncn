package com.nca.produtoservice.services;

import com.nca.produtoservice.domain.model.Produto;
import com.nca.produtoservice.domain.repositories.IProdutoCustomRepository;
import com.nca.produtoservice.exceptions.DAOException;
import com.nca.produtoservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProdutoService {

    private final IProdutoCustomRepository repository;

    @Autowired
    public ProdutoService(IProdutoCustomRepository repository) {
        this.repository = repository;
    }

    public Page<Produto> listar(Pageable pageable) throws DAOException {
        return this.repository.listar(pageable);
    }

    public Produto buscar(String id) throws DAOException, RegistroNaoEncontradoException {
        return this.repository.buscar(id);
    }

    public Collection<Produto> filtrarProdutosPor(String param, String value) throws DAOException {
        return repository.filtrarPor(param, value);
    }

    public Produto cadastrar(Produto produto) throws DAOException {
        return this.repository.salvar(produto);
    }

    public Produto atualizar(Produto produto) throws DAOException {
        return this.repository.atualizar(produto);
    }

    public void remover(String id) throws DAOException {
        this.repository.excluir(id);
    }
}
