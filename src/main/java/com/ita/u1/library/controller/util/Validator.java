package com.ita.u1.library.controller.util;

import com.ita.u1.library.exception.ControllerValidationException;

public class Validator {

    public static String assertNotNullOrEmpty(String s) throws ControllerValidationException {
        if (s == null || s.isEmpty()) {
            throw new ControllerValidationException("Provided value can not be empty.");
        }
        return s;
    }

    public static String[] assertNotNullOrEmptyValues(String [] s) throws ControllerValidationException {
        if (s.length == 0) {
            throw new ControllerValidationException("Provided values can not be empty.");
        }
        return s;
    }


}
