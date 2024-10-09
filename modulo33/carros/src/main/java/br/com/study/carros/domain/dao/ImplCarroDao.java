package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Acessorio;
import br.com.study.carros.domain.model.Carro;
import br.com.study.carros.domain.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImplCarroDao implements ICarroDao {
    @Override
    public List<Carro> listar() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT c FROM Carro c";
        TypedQuery<Carro> query = entityManager.createQuery(jpql, Carro.class);
        List<Carro> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public Carro salvar(Carro carro) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(carro);
        entityManager.getTransaction().commit();
        entityManager.close();
        return carro;
    }

    @Override
    public Carro buscar(Long id) {
        Carro carro = null;
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT c FROM Carro c WHERE c.id =:id";
        TypedQuery<Carro> query = entityManager.createQuery(jpql, Carro.class);
        query.setParameter("id",id);
        List<Carro> resultList = query.getResultList();
        carro = (!resultList.isEmpty()) ? resultList.get(0) : null;
        return carro;
    }

    @Override
    public void atualizar(Carro carro) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(carro);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void remover(Carro carro) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(carro) ? carro : entityManager.merge(carro));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
