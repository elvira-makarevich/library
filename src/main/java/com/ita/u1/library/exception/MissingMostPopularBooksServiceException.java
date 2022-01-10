package com.ita.u1.library.exception;

public class MissingMostPopularBooksServiceException extends RuntimeException {
    public MissingMostPopularBooksServiceException() {
        super();
    }

    public MissingMostPopularBooksServiceException(String message) {
        super(message);
    }

    public MissingMostPopularBooksServiceException(Exception e) {
        super(e);
    }

    public MissingMostPopularBooksServiceException(String message, Exception e) {
        super(message, e);
    }
}
