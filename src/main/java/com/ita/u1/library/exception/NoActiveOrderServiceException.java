package com.ita.u1.library.exception;

public class NoActiveOrderServiceException extends RuntimeException {

    public NoActiveOrderServiceException() {
        super();
    }

    public NoActiveOrderServiceException(String message) {
        super(message);
    }

    public NoActiveOrderServiceException(Exception e) {
        super(e);
    }

    public NoActiveOrderServiceException(String message, Exception e) {
        super(message, e);
    }
}
