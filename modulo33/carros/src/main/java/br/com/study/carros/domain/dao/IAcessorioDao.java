package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Acessorio;

import java.util.List;

public interface IAcessorioDao {

    List<Acessorio> listar();

    Acessorio salvar(Acessorio acessorio);

    Acessorio buscar(Long id);

    void atualizar(Acessorio acessorio);

    void remover(Acessorio acessorio);
}
