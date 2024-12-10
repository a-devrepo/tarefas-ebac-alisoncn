package com.nca.produtoservice.domain.repositories;

import com.nca.produtoservice.domain.model.Produto;
import com.nca.produtoservice.domain.services.SequenceGeneratorService;
import com.nca.produtoservice.enums.StatusRegistro;
import com.nca.produtoservice.exceptions.DAOException;
import com.nca.produtoservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Collection;
import java.util.List;

public class CustomRepositoryImpl implements CustomRepository {

    private final MongoTemplate mongoTemplate;

    private SequenceGeneratorService sequenceGeneratorService;

    public CustomRepositoryImpl(MongoTemplate mongoTemplate, SequenceGeneratorService sequenceGeneratorService) {
        this.mongoTemplate = mongoTemplate;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public Page<Produto> listar(Pageable pageable) throws DAOException {
        try {
            Query query = new Query();
            query.with(pageable);
            query.addCriteria(Criteria
                    .where("status_registro").is(StatusRegistro.ATIVO));
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
            String id = String.valueOf(sequenceGeneratorService.generateSequence(Produto.SEQUENCE_NAME));
            entity.setId(id);
            return mongoTemplate.save(entity);
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar registro:", e);
        }
    }

    @Override
    public Produto buscar(String code) throws RegistroNaoEncontradoException, DAOException {
        Produto produto = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(code).and("status_registro").is(StatusRegistro.ATIVO));
            List<Produto> list = mongoTemplate.find(query, Produto.class);
            if (!list.isEmpty())
                produto = list.get(0);
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
                    Criteria.where(param).regex(value).and("status_registro").is(StatusRegistro.ATIVO));
            List<Produto> produtos = mongoTemplate.find(query, Produto.class, "produto");
            return produtos;
        } catch (Exception e) {
            throw new DAOException("Erro ao buscar registros", e);
        }
    }

    @Override
    public void excluir(String id) throws DAOException {
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
            return mongoTemplate.findAndModify(query, update,
                    FindAndModifyOptions.options().returnNew(true), Produto.class);
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar registro", e);
        }
    }
}
