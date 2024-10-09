package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Acessorio;
import br.com.study.carros.domain.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImplAcessorioDao implements IAcessorioDao {
    @Override
    public List<Acessorio> listar() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT a FROM Acessorio a";
        TypedQuery<Acessorio> query = entityManager.createQuery(jpql, Acessorio.class);
        List<Acessorio> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public Acessorio salvar(Acessorio acessorio) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(acessorio);
        entityManager.getTransaction().commit();
        entityManager.close();
        return acessorio;
    }

    @Override
    public Acessorio buscar(Long id) {
        Acessorio acessorio = null;
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT a FROM Acessorio a WHERE a.id =:id";
        TypedQuery<Acessorio> query = entityManager.createQuery(jpql, Acessorio.class);
        query.setParameter("id",id);
        List<Acessorio> resultList = query.getResultList();
        acessorio = (!resultList.isEmpty()) ? resultList.get(0) : null;
        return acessorio;
    }

    @Override
    public void atualizar(Acessorio acessorio) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(acessorio);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void remover(Acessorio acessorio) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(acessorio) ? acessorio : entityManager.merge(acessorio));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
