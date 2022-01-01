package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewAllBooks implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    public static final int RECORDS_PER_PAGE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static final String PARAM_PAGE = "currentPage";
    public static final String PARAM_NUMBER_OF_PAGES = "numberOfPages";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int numberOfRecords = bookService.getNumberOfBooks();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(PARAM_PAGE) != null) {
            page = Converter.toInt(request.getParameter(PARAM_PAGE));
        }

        List<Book> books = bookService.getAllBooks((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        request.setAttribute(PARAM_NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(PARAM_PAGE, page);

        String json = new Gson().toJson(books);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().write(json);


    }
}
