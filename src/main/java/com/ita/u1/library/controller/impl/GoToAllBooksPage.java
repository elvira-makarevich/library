package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class GoToAllBooksPage implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int RECORDS_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int numberOfRecords = bookService.getNumberOfBooks();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(CURRENT_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        }

        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);

        request.getRequestDispatcher(PATH_ALL_BOOKS_PAGE).forward(request, response);
    }
}
