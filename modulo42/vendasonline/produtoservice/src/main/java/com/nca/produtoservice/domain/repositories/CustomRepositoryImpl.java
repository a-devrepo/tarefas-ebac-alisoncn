package com.nca.produtoservice.domain.repositories;

import com.nca.produtoservice.domain.model.Produto;
import com.nca.produtoservice.enums.StatusRegistro;
import com.nca.produtoservice.exceptions.DAOException;
import com.nca.produtoservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Collection;
import java.util.List;

public class CustomRepositoryImpl implements CustomRepository {

    private final MongoTemplate mongoTemplate;

    public CustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Produto> listar(Pageable pageable) throws DAOException {
        try {
            Query query = new Query();
            query.with(pageable);
            query.addCriteria(Criteria
                    .where("statusRegistro").is(StatusRegistro.ATIVO));
            List<Produto> produtos = mongoTemplate.find(query, Produto.class);
            return createPage(pageable, produtos, query);
        } catch (Exception e) {
            throw new DAOException("Erro ao listar registros: ", e);
        }
    }

    private Page<Produto> createPage(Pageable pageable, List<Produto> produtos, Query query) {
        return PageableExecutionUtils.getPage(produtos, pageable, () -> mongoTemplate.count(query, Produto.class));
    }

    @Override
    public Produto salvar(Produto entity) throws DAOException {
        try {
            return mongoTemplate.save(entity);
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar registro:", e);
        }
    }

    @Override
    public Produto buscar(Long code) throws RegistroNaoEncontradoException, DAOException {
        Produto produto = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("code")
                    .is(code).and("statusRegistro").is(StatusRegistro.ATIVO));
            produto = mongoTemplate.findById(code, Produto.class);
        } catch (Exception e) {
            throw new DAOException("Erro ao buscar registro", e);
        }
        if (produto == null) {
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        }
        return produto;
    }

    public Collection<Produto> filtrarPor(String param, String value) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(
                    Criteria.where("param").is(value));
            List<Produto> produtos = mongoTemplate.find(query, Produto.class, "produto");
            return produtos;
        } catch (Exception e) {
            throw new DAOException("Erro ao buscar registros", e);
        }
    }

    @Override
    public void excluir(Long id) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));

            Update update = new Update();
            update.set("statusRegistro", StatusRegistro.INATIVO);

            mongoTemplate.findAndModify(query, update, Produto.class);
        } catch (Exception e) {
            throw new DAOException("Erro ao excluir registro:", e);
        }
    }

    @Override
    public Produto atualizar(Produto entity) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(entity.getId()));
            Update update = new Update();
            update.set("nome", entity.getNome());
            update.set("descricao", entity.getDescricao());
            update.set("fabricante", entity.getFabricante());
            update.set("statusRegistro", entity.getStatusRegistro());
            update.set("valor", entity.getValor());
            Produto produto = mongoTemplate.findAndModify(query, update, Produto.class);
            return produto;
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar registro", e);
        }
    }
}
