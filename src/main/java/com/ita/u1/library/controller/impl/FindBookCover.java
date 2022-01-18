package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Author;
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

import static com.ita.u1.library.util.ConstantParameter.*;

public class FindBookCover extends AbstractCommand implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(FindBookCover.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int bookId = Converter.toInt(request.getParameter(ID));

        try {
            Book book = bookService.findBookCover(bookId);
            byte[] cover = book.getCovers().get(0);
            sendImage(cover, response);
        } catch (ControllerValidationException e) {
            log.error("Invalid book id.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: FindBookCover.", e);
            throw new ControllerException("Database error. Command: FindBookCover.", e);
        }
    }
}
