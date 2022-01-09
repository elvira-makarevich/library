package com.ita.u1.library.controller.util;

import com.ita.u1.library.exception.ControllerValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {

    private static final Logger log = LogManager.getLogger(Validator.class);

    public static String assertNotNullOrEmpty(String s) throws ControllerValidationException {
        if (s == null || s.isEmpty()) {
            log.error("Provided parameter can not be empty.");
            throw new ControllerValidationException("Provided parameter can not be empty.");
        }
        return s;
    }

}
