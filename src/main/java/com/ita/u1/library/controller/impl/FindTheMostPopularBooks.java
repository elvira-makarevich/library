package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindTheMostPopularBooks extends AbstractCommand implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(FindTheMostPopularBooks.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Book> mostPopularBooks = bookService.findTheMostPopularBooks();
            sendResponseJSON(new Gson().toJson(mostPopularBooks), response);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: FindTheMostPopularBooks.", e);
            throw new ControllerException("Database error. Command: FindTheMostPopularBooks.", e);
        }
    }
}
