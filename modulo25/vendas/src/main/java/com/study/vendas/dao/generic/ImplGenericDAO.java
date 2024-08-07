package com.study.vendas.dao.generic;

import com.study.vendas.anotacoes.Codigo;
import com.study.vendas.dao.Persistente;
import com.study.vendas.exceptions.CodigoNaoEncontradoException;
import com.study.vendas.exceptions.DataBaseException;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ImplGenericDAO<T extends Persistente, U> implements IGenericDAO<T, U> {

  protected final SingletonMap singletonMap;


  public ImplGenericDAO(){
    this.singletonMap = SingletonMap.getInstance();
  }

  private Map<U,T> getMapa(){
    Map<U,T> mapaInterno = (Map<U,T>) this.singletonMap.getMap().get(getTipoClasse());
    if(mapaInterno == null){
      mapaInterno = new HashMap<>();
      this.singletonMap.getMap().put(getTipoClasse(),mapaInterno);
    }
    return mapaInterno;
  }

  @Override
  public Collection<T> listar() {
    return this.getMapa().values();
  }

  @Override
  public T salvar(T entity) throws CodigoNaoEncontradoException, DataBaseException {
    this.gerarCodigo(entity);
    Map<U,T> mapaInterno = getMapa();
    U chave = getCodigo(entity);
    if(mapaInterno.containsKey(chave)){
      throw new DataBaseException("Duplicidade de dados");
    }
    return entity;
  }

  @Override
  public T buscar(U code) throws RegistroNaoEncontradoException {
    Map<U, T> mapaInterno = getMapa();
    T entity = mapaInterno.get(code);
    if(Objects.isNull(entity)){
      throw new RegistroNaoEncontradoException("Registro não encontrado");
    }
    return entity;
  }

  @Override
  public void excluir(U code) throws RegistroNaoEncontradoException {
    this.buscar(code);
    getMapa().remove(code);
  }

  @Override
  public void atualizar(T entity) throws CodigoNaoEncontradoException, RegistroNaoEncontradoException {
    U codigo = getCodigo(entity);
    T entityOld = this.buscar(codigo);
    alterar(entity, entityOld);
  }

  protected abstract void gerarCodigo(T entity);

  public abstract Class<T> getTipoClasse();

  protected abstract void alterar(T entity, T target);
  
  protected U getCodigo(T entity) throws CodigoNaoEncontradoException {
    U codigo = null;
    for (Field field : entity.getClass().getDeclaredFields()) {
      if(field.isAnnotationPresent(Codigo.class)){
        Codigo anotacao = field.getAnnotation(Codigo.class);
        String nomeMetodo = anotacao.value();
        try{
          Method metodo = entity.getClass().getMethod(nomeMetodo);
          codigo = (U) metodo.invoke(entity);
          break;
        }catch (Exception e){
          throw new CodigoNaoEncontradoException("Valor do código do objeto " + entity.getClass()+" não encontrado");
        }
      }
    }
    if(codigo == null){
      throw new CodigoNaoEncontradoException("Valor do código do objeto " + entity.getClass()+" não encontrado");
    }
    return codigo;
  }
}
