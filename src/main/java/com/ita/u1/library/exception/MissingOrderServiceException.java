package com.ita.u1.library.exception;

public class MissingOrderServiceException extends RuntimeException {

    public MissingOrderServiceException() {
        super();
    }

    public MissingOrderServiceException(String message) {
        super(message);
    }

    public MissingOrderServiceException(Exception e) {
        super(e);
    }

    public MissingOrderServiceException(String message, Exception e) {
        super(message, e);
    }
}
