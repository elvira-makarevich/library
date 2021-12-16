package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class GoToAddNewBookPage implements Command {

    public static final String PATH_ADD_BOOK_PAGE = "/WEB-INF/jsp/addNewBook.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher(PATH_ADD_BOOK_PAGE).forward(request, response);

    }
}
