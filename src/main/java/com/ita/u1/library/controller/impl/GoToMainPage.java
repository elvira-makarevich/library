package com.ita.u1.library.controller.impl;


import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.exception.ControllerException;
import com.ita.u1.library.exception.ControllerValidationException;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class GoToMainPage implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(GoToMainPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = DEFAULT_PAGE_NUMBER;

        System.err.close();
        System.setErr(System.out);

        try {
            if (request.getParameter(CURRENT_PAGE) != null) {
                page = Converter.toInt(request.getParameter(CURRENT_PAGE));
            }

            int numberOfRecords = bookService.getNumberOfBooks();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE_BOOKS);

            request.setAttribute(CURRENT_PAGE, page);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.getRequestDispatcher(PATH_MAIN_PAGE).forward(request, response);
        } catch (ControllerValidationException e) {
            log.error("ControllerValidationException. Command: GoToMainPage.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: GoToMainPage.", e);
            throw new ControllerException("Database error. Command: GoToMainPage.", e);
        }
    }
}
