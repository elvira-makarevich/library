package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllBooksPage implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    public static final String PATH_ALL_BOOKS_PAGE = "/WEB-INF/jsp/allBooks.jsp";
    public static final String PARAM_PAGE = "currentPage";
    public static final String PARAM_NUMBER_OF_PAGES = "numberOfPages";

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int RECORDS_PER_PAGE = 2;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int numberOfRecords;
        int numberOfPages;

        numberOfRecords = bookService.getNumberOfRecords();
        numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);// рассчитать количество

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(PARAM_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PARAM_PAGE));
        }


        request.setAttribute(PARAM_PAGE, page);
        request.setAttribute(PARAM_NUMBER_OF_PAGES, numberOfPages);
        // request.setAttribute("limitBooks",books);

        request.getRequestDispatcher(PATH_ALL_BOOKS_PAGE).forward(request, response);
    }
}
