package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.exception.ServiceException;
import com.ita.u1.library.service.impl.AuthorServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FindAuthor implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(request.getContextPath());
        AuthorService authorService = new AuthorServiceImpl();
        Author author = null;
        try {
            author= authorService.findAuthor(55);
            response.setContentType("image/jpeg");
            response.setContentLength(author.getImage().length); // imageBytes - image in bytes
            response.getOutputStream().write(author.getImage());

            request.getParameter("id");
            request.getRequestDispatcher("/WEB-INF/jsp/addNewAuthor.jsp").forward(request, response);

        } catch (ServiceException e) {
            System.out.println("FIASKO, BRATAN");
        }




    }
}
