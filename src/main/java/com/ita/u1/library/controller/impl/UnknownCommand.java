package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnknownCommand implements Command {

    private static final Logger log = LogManager.getLogger(UnknownCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.error("UNKNOWN command.");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
