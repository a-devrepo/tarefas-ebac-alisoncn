package com.nca.clienteservice.domain.repositories;

import com.nca.clienteservice.domain.model.Cliente;
import com.nca.clienteservice.enums.StatusRegistro;
import com.nca.clienteservice.exceptions.DAOException;
import com.nca.clienteservice.exceptions.RegistroNaoEncontradoException;
import com.nca.clienteservice.domain.services.SequenceGeneratorService;
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
    public Page<Cliente> listar(Pageable pageable) throws DAOException {
        try {
            Query query = new Query();
            query.with(pageable);
            query.addCriteria(Criteria
                    .where("statusRegistro").is(StatusRegistro.ATIVO));
            List<Cliente> clientes = mongoTemplate.find(query, Cliente.class);
            return createPage(pageable, clientes, query);
        } catch (Exception e) {
            throw new DAOException("Erro ao listar registros: ", e);
        }
    }

    private Page<Cliente> createPage(Pageable pageable, List<Cliente> clientes, Query query) {
        return PageableExecutionUtils.getPage(clientes, pageable, () -> mongoTemplate.count(query, Cliente.class));
    }

    @Override
    public Cliente salvar(Cliente entity) throws DAOException {
        try {
            String id = String.valueOf(sequenceGeneratorService.generateSequence(Cliente.SEQUENCE_NAME));
            entity.setId(id);
            return mongoTemplate.save(entity);
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar registro:", e);
        }
    }

    @Override
    public Cliente buscar(String code) throws RegistroNaoEncontradoException, DAOException {
        Cliente cliente = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(code).and("status_registro").is(StatusRegistro.ATIVO));
            List<Cliente> list = mongoTemplate.find(query, Cliente.class);
            if (!list.isEmpty()) {
                cliente = list.get(0);
            }
        } catch (Exception e) {
            throw new DAOException("Erro ao buscar registro", e);
        }
        if (cliente == null) {
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        }
        return cliente;
    }

    public Collection<Cliente> filtrarPor(String param, String value) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(
                    Criteria.where(param).is(value));
            List<Cliente> clientes = mongoTemplate.find(query, Cliente.class, "cliente");
            return clientes;
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

            mongoTemplate.findAndModify(query, update, Cliente.class);
        } catch (Exception e) {
            throw new DAOException("Erro ao excluir registro:", e);
        }
    }

    @Override
    public Cliente atualizar(Cliente entity) throws DAOException {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(entity.getId()));
            Update update = new Update();
            update.set("nome", entity.getNome());
            update.set("email", entity.getEmail());
            update.set("endereco", entity.getEndereco());
            update.set("status", entity.getStatus());
            update.set("statusRegistro", entity.getStatusRegistro());
            Cliente cliente = mongoTemplate.findAndModify(query, update,
                    FindAndModifyOptions.options().returnNew(true), Cliente.class);
            return cliente;
        } catch (Exception e) {
            throw new DAOException("Erro ao excluir registro", e);
        }
    }
}
