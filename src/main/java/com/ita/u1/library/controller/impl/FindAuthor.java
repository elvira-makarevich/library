package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.ServiceProvider;
import com.ita.u1.library.service.exception.ServiceException;
import com.ita.u1.library.service.impl.AuthorServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindAuthor implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Author> authors = null;

        try {
            authors = authorService.findAuthor(55);
            //    response.setContentType("image/jpeg");
            //     response.setContentLength(author.getImage().length); // imageBytes - image in bytes
            //    response.getOutputStream().write(author.getImage());
            //    request.getRequestDispatcher("/WEB-INF/jsp/addNewAuthor.jsp").forward(request, response);

            String json = new Gson().toJson(authors);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);
        } catch (ServiceException e) {
            System.out.println("FIASKO, BRATAN");
        }


    }
}
