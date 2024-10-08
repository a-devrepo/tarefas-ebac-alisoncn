package br.com.estudojpa.domain.dao;

import br.com.estudojpa.domain.model.Produto;
import java.util.List;
import javax.persistence.*;

public class ImplProdutoDAO implements IProdutoDAO {

  EntityManagerFactory managerFactory;

  public ImplProdutoDAO() {
    this.managerFactory = Persistence.createEntityManagerFactory("vendas-jpa");
  }

  @Override
  public List<Produto> listar() {
    EntityManager manager = managerFactory.createEntityManager();
    String jpql = "SELECT p FROM Produto p";
    TypedQuery<Produto> query = manager.createQuery(jpql, Produto.class);
    List<Produto> produtos = query.getResultList();
    manager.close();
    return produtos;
  }

  @Override
  public Produto salvar(Produto produto) {
    EntityManager entityManager = managerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(produto);
    entityManager.getTransaction().commit();
    entityManager.close();
    return produto;
  }

  @Override
  public List<Produto> buscar(String codigo) {
    EntityManager manager = managerFactory.createEntityManager();
    String jpql = "SELECT p FROM Produto p WHERE p.codigo =:codigo";
    TypedQuery<Produto> query = manager.createQuery(jpql, Produto.class);
    query.setParameter("codigo", codigo);
    List<Produto> produtos = query.getResultList();
    manager.close();
    return produtos;
  }

  @Override
  public void atualizar(Produto produto) {
    EntityManager manager = managerFactory.createEntityManager();
    manager.getTransaction().begin();
    manager.merge(produto);
    manager.getTransaction().commit();
    manager.close();
  }

  @Override
  public void remover(Produto produto) {
    EntityManager manager = managerFactory.createEntityManager();
    manager.getTransaction().begin();
    manager.remove(manager.contains(produto)? produto: manager.merge(produto));
    manager.getTransaction().commit();
    manager.close();
  }
}
