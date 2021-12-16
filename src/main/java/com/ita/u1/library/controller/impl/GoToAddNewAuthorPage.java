package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GoToAddNewAuthorPage implements Command {

    public static final String PATH_ADD_AUTHOR_PAGE = "/WEB-INF/jsp/addNewAuthor.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher(PATH_ADD_AUTHOR_PAGE).forward(request, response);
    }
}
