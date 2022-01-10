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

public class FindTheMostPopularBooks implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(FindTheMostPopularBooks.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> mostPopularBooks = bookService.findTheMostPopularBooks();
            String json = new Gson().toJson(mostPopularBooks);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: FindAuthor.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: FindAuthor.", e);
        } catch (DAOException e) {
            log.error("Database error. Command: FindTheMostPopularBooks.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: FindTheMostPopularBooks.", e);
        } catch (MissingMostPopularBooksServiceException e) {
            log.error("Nobody borrowed books from the library!", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("Nobody borrowed books from the library!", e);
        }
    }
}
