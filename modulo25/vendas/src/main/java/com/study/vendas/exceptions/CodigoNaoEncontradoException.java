package com.study.vendas.exceptions;

public class CodigoNaoEncontradoException extends  Exception{

    public CodigoNaoEncontradoException(String message) {
        super(message,null);
    }

    public CodigoNaoEncontradoException(String message,Throwable e) {
        super(message,e);
    }
}
