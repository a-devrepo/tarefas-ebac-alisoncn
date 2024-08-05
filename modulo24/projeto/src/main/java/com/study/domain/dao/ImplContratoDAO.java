package com.study.domain.dao;

import com.study.anotacoes.Codigo;
import com.study.domain.Contrato;
import java.lang.reflect.Field;
import java.security.SecureRandom;

public class ImplContratoDAO extends ImplGenericDAO<Contrato, Long> implements IContratoDAO {

  @Override
  protected Long getCodigo(Contrato entity) {

      Long codigo = null;
      for (Field field : Contrato.class.getDeclaredFields()) {
        if(field.isAnnotationPresent(Codigo.class)){
          try{
              field.setAccessible(true);
            codigo = (Long) field.get(entity);
            break;
          }catch (Exception e){
            throw new RuntimeException("Erro: "+ e.getLocalizedMessage());
          }
        }
      }
      if(codigo == null){
        throw new RuntimeException("O objeto não possui código");
      }
      return codigo;
  }

  @Override
  protected void gerarCodigo(Contrato entity) {
    SecureRandom secureRandom = new SecureRandom();
    long codigo = Math.abs(secureRandom.nextLong());
    entity.setCodigo(codigo);
  }

  @Override
  protected void atualizar(Contrato entity, Contrato target) {
    target.setAssunto(entity.getAssunto());
  }
}
