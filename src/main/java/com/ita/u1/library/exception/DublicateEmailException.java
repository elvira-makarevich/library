package com.ita.u1.library.exception;

public class DublicateEmailException extends RuntimeException{

    public DublicateEmailException() {
        super();
    }

    public DublicateEmailException(String message) {
        super(message);
    }

    public DublicateEmailException(Exception e) {
        super(e);
    }

    public DublicateEmailException(String message, Exception e) {
        super(message, e);
    }
}
