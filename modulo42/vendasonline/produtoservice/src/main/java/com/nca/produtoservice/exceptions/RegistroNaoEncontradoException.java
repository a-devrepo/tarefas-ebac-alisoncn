package com.nca.produtoservice.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException{
    public RegistroNaoEncontradoException(String message) {
        super(message);
    }
}
