package com.ita.u1.library.exception;

public class ControllerValidationException extends RuntimeException {
    public ControllerValidationException() {
        super();
    }

    public ControllerValidationException(String message) {
        super(message);
    }

    public ControllerValidationException(Exception e) {
        super(e);
    }

    public ControllerValidationException(String message, Exception e) {
        super(message, e);
    }
}
