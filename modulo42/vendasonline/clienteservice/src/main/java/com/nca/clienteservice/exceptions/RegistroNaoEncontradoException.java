package com.nca.clienteservice.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException{
    public RegistroNaoEncontradoException(String message) {
        super(message);
    }
}
