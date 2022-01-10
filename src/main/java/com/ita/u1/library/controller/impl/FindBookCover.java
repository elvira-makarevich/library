package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindBookCover implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Converter.toInt(request.getParameter("id"));
        try {
            Book book= bookService.findBookCover(id);
            byte [] cover = book.getCovers().get(0);
            System.out.println(cover);
            response.setContentType("image/jpeg");
            response.setContentLength(cover.length);
            System.out.println(cover.length);
            response.getOutputStream().write(cover);
        } catch (DAOConnectionPoolException e) {
            //перевести на страницу с сообщением:проблемы доступа к бд
            e.printStackTrace();
        } catch (DAOException e) {
            //перевести на страницу с сообщением: проблемы при запросе информации из бд
            e.printStackTrace();
        }

    }
}
