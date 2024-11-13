package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.ImplGenericDAO;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.exceptions.DAOException;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Stateless
public class ImplClienteDAO extends ImplGenericDAO<Cliente, Long> implements IClienteDAO {
    public ImplClienteDAO() {
        super(Cliente.class);
    }

    public Collection<Cliente> findBy(String param, String value) throws DAOException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
            Root<Cliente> root = criteriaQuery.from(Cliente.class);
            criteriaQuery.select(root).where(criteriaBuilder.like(root.get(param), "%" + value + "%"));
            List<Cliente> resultList = entityManager.createQuery(criteriaQuery).getResultList();
            return resultList;
        } catch (Exception e) {
            throw new DAOException("Erro ao filtrar Cliente: ", e);
        }
    }
}
