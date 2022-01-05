package com.ita.u1.library.exception;

public class NoSuchImageAuthorServiceException extends RuntimeException {

    public NoSuchImageAuthorServiceException() {
        super();
    }

    public NoSuchImageAuthorServiceException(String message) {
        super(message);
    }

    public NoSuchImageAuthorServiceException(Exception e) {
        super(e);
    }

    public NoSuchImageAuthorServiceException(String message, Exception e) {
        super(message, e);
    }
}
