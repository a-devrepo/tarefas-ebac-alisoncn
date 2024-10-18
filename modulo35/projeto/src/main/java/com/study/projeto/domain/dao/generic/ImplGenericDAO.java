package com.study.projeto.domain.dao.generic;

import com.study.projeto.domain.dao.util.EntityManagerProvider;
import com.study.projeto.domain.model.Persistente;
import com.study.projeto.enums.StatusRegistro;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class ImplGenericDAO<T extends Persistente, E extends Serializable>
    implements IGenericDAO<T, E> {

  protected EntityManagerProvider entityManagerProvider;
  protected EntityManager entityManager;
  private final Class<T> persistenteClass;

  public ImplGenericDAO(Class<T> persistenteClass) {
    this.persistenteClass = persistenteClass;
    this.entityManagerProvider = EntityManagerProvider.getInstance();
  }

  @Override
  public Collection<T> listar() {
    entityManager = entityManagerProvider.getEntityManager();
    return entityManager.createQuery(getSelectQuery(), this.persistenteClass).getResultList();
  }

  @Override
  public T salvar(T entity) throws DAOException{
    try {
      entityManager = entityManagerProvider.getEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(entity);
      entityManager.getTransaction().commit();
      entityManager.close();
      return entity;
    } catch (Exception e) {
      throw new DAOException("Erro ao salvar registro: ", e);
    }
  }

  @Override
  public T buscar(E code) throws RegistroNaoEncontradoException, DAOException {
    List<T> resultList = null;
    try {
      entityManager = entityManagerProvider.getEntityManager();
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(persistenteClass);
      Root<T> root = criteriaQuery.from(persistenteClass);
      criteriaQuery.select(root);
      Predicate predicateId = criteriaBuilder.equal(root.get("id"), code);
      Predicate predicateStatus = criteriaBuilder.equal(root.get("statusRegistro"), StatusRegistro.ATIVO);
      Predicate finalPredicate = criteriaBuilder.and(predicateId,predicateStatus);
      criteriaQuery.where(finalPredicate);
      resultList = entityManager.createQuery(criteriaQuery).getResultList();
      entityManager.close();
    } catch (Exception e) {
      throw new DAOException("Erro ao buscar registro: ", e);
    }
    if (resultList.isEmpty()) {
      throw new RegistroNaoEncontradoException("Registro não encontrado");
    }
    return resultList.get(0);
  }

  @Override
  public void excluir(T entity) throws DAOException {
    try {
      entityManager = entityManagerProvider.getEntityManager();
      entityManager.getTransaction().begin();
      entity.setStatusRegistro(StatusRegistro.INATIVO);
      entityManager.merge(entity);
      entityManager.getTransaction().commit();
      entityManager.close();
    } catch (Exception e) {
      throw new DAOException("Erro ao excluir registro: ", e);
    }
  }

  @Override
  public T atualizar(T entity) throws DAOException {
    try {
      entityManager = entityManagerProvider.getEntityManager();
      entityManager.getTransaction().begin();
      entityManager.merge(entity);
      entityManager.getTransaction().commit();
      entityManager.close();
      return entity;
    } catch (Exception e) {
      throw new DAOException("Erro ao atualizar registro: ", e);
    }
  }

  protected String getSelectQuery() {
    StringBuilder query = new StringBuilder();
    query.append("SELECT obj FROM ");
    query.append(this.persistenteClass.getSimpleName());
    query.append(" obj");
    query.append(" WHERE obj.statusRegistro = com.study.projeto.enums.StatusRegistro.ATIVO");
    return query.toString();
  }
}
