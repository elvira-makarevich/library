package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class GoToCopyBookEditPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(TITLE, request.getParameter(TITLE));
        request.setAttribute(COST_PER_DAY, request.getParameter(COST_PER_DAY));
        request.setAttribute(COPY_ID, request.getParameter(COPY_ID));
        request.getRequestDispatcher(PATH_COPY_BOOK_EDIT_PAGE).forward(request, response);
    }
}