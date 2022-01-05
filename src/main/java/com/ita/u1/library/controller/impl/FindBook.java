package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindBook implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = Validator.assertNotNullOrEmpty(request.getParameter("title"));

        try {
            List<Book> books = bookService.findBook(title);

            String json = new Gson().toJson(books);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);

        } catch (DAOConnectionPoolException e) {
            //перевести на страницу с сообщением:проблемы доступа к бд
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOException e) {
            //перевести на страницу с сообщением: проблемы при запросе информации из бд
            e.printStackTrace();
        }
    }
}
