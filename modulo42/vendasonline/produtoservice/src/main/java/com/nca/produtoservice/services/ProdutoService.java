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

    private IProdutoCustomRepository repository;

    @Autowired
    public ProdutoService(IProdutoCustomRepository repository) {
        this.repository = repository;
    }

    public Page<Produto> listar(Pageable pageable) throws DAOException {
        return this.repository.listar(pageable);
    }

    public Produto buscar(Long id) throws DAOException, RegistroNaoEncontradoException {
        return this.repository.buscar(id);
    }

    public Collection<Produto> filtrarProdutosPor(String param, String value) throws DAOException {
        Collection<Produto> produtos = repository.filtrarPor(param, value);
        return produtos;
    }

    public Produto cadastrar(Produto produto) throws DAOException {
        return this.repository.salvar(produto);
    }

    public Produto atualizar(Produto produto) throws DAOException {
        return this.repository.atualizar(produto);
    }

    public void remover(Long id) throws DAOException {
        this.repository.excluir(id);
    }
}
