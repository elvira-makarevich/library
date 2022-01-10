package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.exception.ControllerException;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.exception.ServiceException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class FindBookCover implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(FindBookCover.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Converter.toInt(request.getParameter(ID));
        try {
            Book book = bookService.findBookCover(id);
            byte[] cover = book.getCovers().get(0);
            response.setContentType("image/jpeg");
            response.setContentLength(cover.length);
            response.getOutputStream().write(cover);
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: FindBookCover.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: FindBookCover.", e);
        } catch (DAOException e) {
            log.error("Database error. Command: FindBookCover.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: FindBookCover.", e);
        }

    }
}
