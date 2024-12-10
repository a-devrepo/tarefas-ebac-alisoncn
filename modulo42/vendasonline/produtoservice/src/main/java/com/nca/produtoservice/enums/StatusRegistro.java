package com.nca.produtoservice.enums;

import java.util.Objects;

public enum StatusRegistro {
    ATIVO,
    INATIVO;

    public static StatusRegistro getByname(String name) {
        StatusRegistro status = null;
        for (StatusRegistro statusVenda : StatusRegistro.values()) {
            if (statusVenda.name().equals(name.toUpperCase())) {
                status = statusVenda;
            }
        }
        if (Objects.isNull(status)) {
            throw new RuntimeException("Status de registro não encontrado");
        }
        return status;
    }
}