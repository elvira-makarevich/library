package com.ita.u1.library.exception;

public class DAONoSuchImageAuthorException extends RuntimeException {

    public DAONoSuchImageAuthorException() {
        super();
    }

    public DAONoSuchImageAuthorException(String message) {
        super(message);
    }

    public DAONoSuchImageAuthorException(Exception e) {
        super(e);
    }

    public DAONoSuchImageAuthorException(String message, Exception e) {
        super(message, e);
    }
}
