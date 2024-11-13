package com.study.projeto.domain.dao.impl.venda;

import com.study.projeto.domain.dao.generic.ImplGenericDAO;
import com.study.projeto.domain.model.relational.Venda;
import com.study.projeto.enums.StatusRegistro;
import com.study.projeto.exceptions.*;
import java.util.List;
import javax.persistence.TypedQuery;

public class ImplVendaDAO extends ImplGenericDAO<Venda, Long> implements IVendaDAO {

  public ImplVendaDAO() {
    super(Venda.class);
  }

  @Override
  public Venda salvar(Venda entity) throws DAOException {
    try {
      verificarDuplicidade(entity);
      entityManager.persist(entity);
      return entity;
    } catch (RegistroDuplicadoException e) {
      throw new DAOException(e.getMessage());
    } catch (Exception e) {
      throw new DAOException("Erro ao cadastrar venda: " + e);
    }
  }

  @Override
  public void excluir(Venda entity) throws DAOException {
    try {
      entity.setStatusRegistro(StatusRegistro.INATIVO);
      entity.getItens().forEach(itemVenda -> itemVenda.setStatusRegistro(StatusRegistro.INATIVO));
      entityManager.merge(entity);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      throw new DAOException("Erro ao excluir registro: ", e);
    }
  }

  public Venda buscarVendaProdutos(Venda venda) throws DAOException {
    try {
      StringBuilder jpql = new StringBuilder();
      jpql.append("SELECT v FROM Venda AS v ");
      jpql.append("JOIN FETCH v.itens iv ");
      jpql.append("WHERE v.id = :idVenda ");
      jpql.append("AND v.statusRegistro = com.study.projeto.enums.StatusRegistro.ATIVO ");
      jpql.append("AND iv.statusRegistro = com.study.projeto.enums.StatusRegistro.ATIVO ");

      TypedQuery<Venda> vendaQuery = entityManager.createQuery(jpql.toString(), Venda.class);
      vendaQuery.setParameter("idVenda", venda.getId());
      List<Venda> list = vendaQuery.getResultList();

      if (list.isEmpty()) {
        return venda;
      }
      return list.get(0);
    } catch (Exception e) {
      throw new DAOException("Erro ao buscar Venda com lista de itens: ", e);
    }
  }

  private void verificarDuplicidade(Venda venda) throws RegistroDuplicadoException {
    String selectQuery = getSelectQuery();
    StringBuilder query = new StringBuilder();
    query.append(selectQuery);
    query.append(" AND obj.codigo =: codigoVenda");
    TypedQuery<Venda> typedQuery = entityManager.createQuery(query.toString(), Venda.class);
    typedQuery.setParameter("codigoVenda", venda.getCodigo());
    List<Venda> vendas = typedQuery.getResultList();

    if (!vendas.isEmpty()) {
      throw new RegistroDuplicadoException("Já existe registro com o código: " + venda.getCodigo());
    }
  }
}
