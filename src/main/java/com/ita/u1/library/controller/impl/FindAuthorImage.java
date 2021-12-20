package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.ServiceProvider;
import com.ita.u1.library.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindAuthorImage implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Author author = authorService.findAuthorImage(Integer.parseInt(request.getParameter("id")));
            response.setContentType("image/jpeg");
            response.setContentLength(author.getImage().length);
            response.getOutputStream().write(author.getImage());
        } catch (DAOConnectionPoolException e) {
            //перевести на страницу с сообщением:проблемы доступа к бд
            e.printStackTrace();
        } catch (DAOException e) {
            //перевести на страницу с сообщением: проблемы при запросе информации из бд
            e.printStackTrace();
        }
    }
}
