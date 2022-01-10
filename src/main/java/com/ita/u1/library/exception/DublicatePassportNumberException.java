package com.ita.u1.library.exception;

public class DublicatePassportNumberException extends RuntimeException{
    public DublicatePassportNumberException() {
        super();
    }

    public DublicatePassportNumberException(String message) {
        super(message);
    }

    public DublicatePassportNumberException(Exception e) {
        super(e);
    }

    public DublicatePassportNumberException(String message, Exception e) {
        super(message, e);
    }
}
