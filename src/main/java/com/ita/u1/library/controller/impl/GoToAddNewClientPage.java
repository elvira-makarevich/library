package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAddNewClientPage implements Command {

    public static final String PATH_ADD_CLIENT_PAGE = "/WEB-INF/jsp/addNewClient.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher(PATH_ADD_CLIENT_PAGE).forward(request, response);

    }
}
