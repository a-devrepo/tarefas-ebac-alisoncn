package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Marca;
import br.com.study.carros.domain.util.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ImplMarcaDao implements IMarcaDao {

  @Override
  public List<Marca> listar() {
    EntityManager entityManager = JpaUtil.getEntityManager();
    String jpql = "SELECT m FROM Marca m";
    TypedQuery<Marca> query = entityManager.createQuery(jpql, Marca.class);
    List<Marca> resultList = query.getResultList();
    entityManager.close();
    return resultList;
  }

  @Override
  public Marca salvar(Marca marca) {
    EntityManager entityManager = JpaUtil.getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(marca);
    entityManager.getTransaction().commit();
    entityManager.close();
    return marca;
  }

  @Override
  public Marca buscar(Long id) {
    Marca marca = null;
    EntityManager entityManager = JpaUtil.getEntityManager();
    String jpql = "SELECT m FROM Marca m WHERE m.id =:id";
    TypedQuery<Marca> query = entityManager.createQuery(jpql, Marca.class);
    query.setParameter("id", id);
    List<Marca> resultList = query.getResultList();
    marca = (!resultList.isEmpty()) ? resultList.get(0) : null;
    return marca;
  }

  @Override
  public void atualizar(Marca marca) {
    EntityManager entityManager = JpaUtil.getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.merge(marca);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  @Override
  public void remover(Marca marca) {
    EntityManager entityManager = JpaUtil.getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.remove(entityManager.contains(marca) ? marca : entityManager.merge(marca));
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
