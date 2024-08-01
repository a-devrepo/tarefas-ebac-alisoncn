package com.study.projeto.domain.dao;

import com.study.projeto.anotacoes.Codigo;
import com.study.projeto.domain.model.Pessoa;
import java.lang.reflect.Field;
import java.security.SecureRandom;

public class ImplPessoaDAO extends ImplGenericDAO<Pessoa,Long> implements IPessoaDAO {
    @Override
    protected void atualizar(Pessoa entity, Pessoa target) {
        target.setNome(entity.getNome());
        target.setSobreNome(entity.getSobreNome());
        target.setIdade(entity.getIdade());
    }

    @Override
    protected void gerarId(Pessoa entity) {
        SecureRandom secureRandom = new SecureRandom();
        long codigo = Math.abs(secureRandom.nextLong());
        entity.setId(codigo);
    }

    @Override
    protected Long getCodigo(Pessoa entity) {
        for (Field field : Pessoa.class.getFields()) {
            if (field.isAnnotationPresent(Codigo.class)) {
                try {
                    return (Long) field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
