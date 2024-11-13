package com.study.projeto.domain.dao.impl.produto;

import com.study.projeto.domain.dao.generic.ImplGenericDAO;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.exceptions.DAOException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class ImplProdutoDAO extends ImplGenericDAO<Produto, Long> implements IProdutoDAO {
    public ImplProdutoDAO() {
        super(Produto.class);
    }

    public Collection<Produto> findBy(String param, String value) throws DAOException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
            Root<Produto> root = criteriaQuery.from(Produto.class);
            criteriaQuery.select(root).where(criteriaBuilder.like(root.get(param), "%" + value + "%"));
            List<Produto> resultList = entityManager.createQuery(criteriaQuery).getResultList();
            return resultList;
        } catch (Exception e) {
            throw new DAOException("Erro ao filtrar Produto: ", e);
        }
    }
}
