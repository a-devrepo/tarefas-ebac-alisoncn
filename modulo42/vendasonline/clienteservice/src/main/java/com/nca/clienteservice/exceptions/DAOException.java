package com.nca.clienteservice.exceptions;

public class DAOException extends RuntimeException {
    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(String msg, Exception e) {
        super(msg, e);
    }
}
