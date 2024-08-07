package com.study.vendas.dao;

import com.study.vendas.dao.generic.ImplGenericDAO;
import com.study.vendas.domain.Cliente;
import java.security.SecureRandom;

public class ImplClienteDAO extends ImplGenericDAO<Cliente,Long> implements IClienteDAO {

    @Override
    protected void gerarCodigo(Cliente entity) {
        SecureRandom secureRandom = new SecureRandom();
        long codigo = Math.abs(secureRandom.nextLong());
        entity.setCodigo(codigo);
    }

    @Override
    public Class<Cliente> getTipoClasse() {
        return Cliente.class;
    }

    @Override
    protected void alterar(Cliente entity, Cliente target) {
        target.setNome(entity.getNome());
        target.setEndereco(entity.getEndereco());
        target.setCidade(entity.getCidade());
        target.setNumero(entity.getNumero());
        target.setEstado(entity.getEstado());
        target.setTelefone(entity.getTelefone());
    }
}
