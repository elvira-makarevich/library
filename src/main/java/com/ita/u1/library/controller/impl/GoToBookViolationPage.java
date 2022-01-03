package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToBookViolationPage implements Command {

    public static final String PATH_BOOK_VIOLATION_PAGE = "/WEB-INF/jsp/bookViolation.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", request.getParameter("title"));
        request.setAttribute("costPerDay", request.getParameter("costPerDay"));
        request.setAttribute("copyId", request.getParameter("copyId"));
        request.setAttribute("orderId", request.getParameter("orderId"));
        request.getRequestDispatcher(PATH_BOOK_VIOLATION_PAGE).forward(request, response);
    }
}
