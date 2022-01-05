package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.exception.NoSuchImageAuthorServiceException;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class FindAuthor implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lastName = Validator.assertNotNullOrEmpty(request.getParameter("lastName"));



        try {
            List<Author> authors = authorService.findAuthor(lastName);
            String json = new Gson().toJson(authors);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);

        } catch (DAOConnectionPoolException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            //перевести на страницу с сообщением:проблемы доступа к бд

        } catch (DAOException e) {
            //перевести на страницу с сообщением: проблемы при запросе информации из бд
            e.printStackTrace();
        } catch (NoSuchImageAuthorServiceException e) {
            //перевести на страницу с сообщением: в бд не существует изображения с указанным ID
            e.printStackTrace();
        }

    }


}
