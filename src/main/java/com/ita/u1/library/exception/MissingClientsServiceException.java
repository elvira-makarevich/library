package com.ita.u1.library.exception;

public class MissingClientsServiceException extends RuntimeException {

    public MissingClientsServiceException() {
        super();
    }

    public MissingClientsServiceException(String message) {
        super(message);
    }

    public MissingClientsServiceException(Exception e) {
        super(e);
    }

    public MissingClientsServiceException(String message, Exception e) {
        super(message, e);
    }
}
