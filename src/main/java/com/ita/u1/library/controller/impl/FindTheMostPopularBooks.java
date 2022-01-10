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

public class FindTheMostPopularBooks implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Book> mostPopularBooks = bookService.findTheMostPopularBooks();
        String json = new Gson().toJson(mostPopularBooks);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().write(json);
    }
}
