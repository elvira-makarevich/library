package com.ita.u1.library.exception;

public class DAOConnectionPoolException extends RuntimeException {

    public DAOConnectionPoolException() {
        super();
    }

    public DAOConnectionPoolException(String message) {
        super(message);
    }

    public DAOConnectionPoolException(Exception e) {
        super(e);
    }

    public DAOConnectionPoolException(String message, Exception e) {
        super(message, e);
    }
}
