package com.study.projeto;

import java.util.Random;

public class ListaCarros extends GenericList<Carro, Long> implements IListaCarros {

    @Override
    protected void atualizar(Carro entity, Carro target) {
        target.setMarca(entity.getMarca());
        target.setModelo(entity.getModelo());
        target.setAutomatico(target.isAutomatico());
    }

    @Override
    protected void gerarId(Carro entity) {
        entity.setCodigo(new Random().nextLong());
    }
}
