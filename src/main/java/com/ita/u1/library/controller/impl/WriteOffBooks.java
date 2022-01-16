package com.ita.u1.library.controller.impl;


import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.CopyBook;
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

import static com.ita.u1.library.util.ConstantParameter.COPY_ID;

public class WriteOffBooks implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(WriteOffBooks.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<CopyBook> copyBooks = Converter.toListCopies(request.getParameterValues(COPY_ID));

        try {
            bookService.writeBooksOff(copyBooks);
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: WriteOffBooks.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: WriteOffBooks.", e);
        } catch (DAOException e) {
            log.error("Database error. Command: WriteOffBooks.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. WriteOffBooks.", e);
        } catch (ServiceException e) {
            log.error("The list contains copy books with the same id.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("The list contains copy books with the same id.", e);
        }

    }
}
