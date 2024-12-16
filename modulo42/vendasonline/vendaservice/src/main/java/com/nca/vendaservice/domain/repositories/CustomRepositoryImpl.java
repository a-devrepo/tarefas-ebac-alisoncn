package com.nca.vendaservice.domain.repositories;

import com.nca.vendaservice.domain.model.ItemVenda;
import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.domain.services.SequenceGeneratorService;
import com.nca.vendaservice.enums.StatusRegistro;
import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.exceptions.RegistroDuplicadoException;
import com.nca.vendaservice.exceptions.RegistroNaoEncontradoException;
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
    public Page<Venda> listar(Pageable pageable) throws DAOException {
        try {
            Query query = new Query();
            query.with(pageable);
            query.addCriteria(Criteria
                    .where("status_registro").is(StatusRegistro.ATIVO));
            List<Venda> list = mongoTemplate.find(query, Venda.class);
            return createPage(pageable, list, query);
        } catch (Exception e) {
            throw new DAOException("Erro ao listar registros: ", e);
        }
    }

    private Page<Venda> createPage(Pageable pageable, List<Venda> vendas, Query query) {
        return PageableExecutionUtils.getPage(vendas, pageable, () -> mongoTemplate.count(query, Venda.class));
    }

    @Override
    public Venda salvar(Venda entity) throws DAOException {
        try {
            verificarDuplicidade(entity);
            String id = String.valueOf(sequenceGeneratorService.generateSequence(Venda.SEQUENCE_NAME));
            entity.setId(id);
            entity.getItens().forEach(item ->
            {
                item.setId(String.valueOf(sequenceGeneratorService.generateSequence(ItemVenda.SEQUENCE_NAME)));
            });
            return mongoTemplate.save(entity);
        } catch (RegistroDuplicadoException e) {
            throw new DAOException(e.getMessage());
        } catch (Exception e) {
            throw new DAOException("Erro ao cadastrar venda: ", e);
        }
    }

    @Override
    public Venda buscar(String code) throws RegistroNaoEncontradoException, DAOException {
        Venda produto = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(code).and("status_registro").is(StatusRegistro.ATIVO));
            List<Venda> list = mongoTemplate.find(query, Venda.class);
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

    public Collection<Venda> filtrarPor(String param, String value) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(
                    Criteria.where(param).regex(value).and("status_registro").is(StatusRegistro.ATIVO));
            List<Venda> produtos = mongoTemplate.find(query, Venda.class, "produto");
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

            mongoTemplate.findAndModify(query, update, Venda.class);
        } catch (Exception e) {
            throw new DAOException("Erro ao excluir registro:", e);
        }
    }

    @Override
    public Venda atualizar(Venda entity) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(entity.getId()));
            Update update = new Update();
//            update.set("nome", entity.getNome());
//            update.set("descricao", entity.getDescricao());
//            update.set("fabricante", entity.getFabricante());
//            update.set("statusRegistro", entity.getStatusRegistro());
//            update.set("valor", entity.getValor());
            return mongoTemplate.findAndModify(query, update,
                    FindAndModifyOptions.options().returnNew(true), Venda.class);
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar registro", e);
        }
    }

    private void verificarDuplicidade(Venda venda) throws RegistroDuplicadoException {
        Query query = new Query();
        query.addCriteria(Criteria.where("codigo").is(venda.getCodigo())
                .and("status_registro").is(StatusRegistro.ATIVO));
        List<Venda> list = mongoTemplate.find(query, Venda.class);
        if (!list.isEmpty()) {
            throw new RegistroDuplicadoException("Já existe registro com o código: " + venda.getCodigo());
        }
    }
}
