package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
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

import static com.ita.u1.library.util.ConstantParameter.*;


public class ViewAllBooks extends AbstractCommand implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(ViewAllBooks.class);

    public static final int RECORDS_PER_PAGE = 20;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(CURRENT_PAGE) != null) {
            page = Converter.toInt(request.getParameter(CURRENT_PAGE));
        }

        try {
            int numberOfRecords = bookService.getNumberOfBooks();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

            List<Book> books = bookService.getAllBooks((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            sendResponseJSON(new Gson().toJson(books), response);

        } catch (ControllerValidationException e) {
            log.error("ControllerValidationException. Command: ViewAllBooks.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: ViewAllBooks.", e);
            throw new ControllerException("Database error. Command: ViewAllBooks.", e);
        }
    }
}
