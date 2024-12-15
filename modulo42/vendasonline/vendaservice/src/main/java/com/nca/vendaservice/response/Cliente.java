package com.nca.vendaservice.response;

import com.nca.vendaservice.enums.StatusCliente;
import com.nca.vendaservice.enums.StatusRegistro;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cliente {

    private String id;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private Endereco endereco;

    private StatusCliente status;

    private StatusRegistro statusRegistro;

    public Cliente() {
        this.status = StatusCliente.ATIVO;
        this.statusRegistro = StatusRegistro.ATIVO;
    }

    public Cliente(String id, String nome, String cpf, String telefone, String email,
                   Endereco endereco, StatusCliente status, StatusRegistro statusRegistro) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.status = status;
        this.statusRegistro = statusRegistro;
    }
}
