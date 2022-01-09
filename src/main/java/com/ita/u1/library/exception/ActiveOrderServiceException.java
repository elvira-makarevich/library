package com.ita.u1.library.exception;

public class ActiveOrderServiceException extends RuntimeException  {

    public ActiveOrderServiceException() {
        super();
    }

    public ActiveOrderServiceException(String message) {
        super(message);
    }

    public ActiveOrderServiceException(Exception e) {
        super(e);
    }

    public ActiveOrderServiceException(String message, Exception e) {
        super(message, e);
    }
}
