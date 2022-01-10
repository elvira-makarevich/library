package com.ita.u1.library.exception;

public class MissingBooksServiceException extends RuntimeException {
    public MissingBooksServiceException() {
        super();
    }

    public MissingBooksServiceException(String message) {
        super(message);
    }

    public MissingBooksServiceException(Exception e) {
        super(e);
    }

    public MissingBooksServiceException(String message, Exception e) {
        super(message, e);
    }
}
