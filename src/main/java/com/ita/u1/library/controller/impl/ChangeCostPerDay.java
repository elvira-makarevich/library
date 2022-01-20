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
import java.math.BigDecimal;

import static com.ita.u1.library.util.ConstantParameter.*;

public class ChangeCostPerDay implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(ChangeCostPerDay.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            BigDecimal newCostPerDay = Converter.toBigDecimal(request.getParameter(NEW_COST_PER_DAY));
            int copyId = Converter.toInt(request.getParameter(COPY_ID));
            CopyBook copyBook = new CopyBook(copyId, newCostPerDay);

            bookService.changeCostPerDay(copyBook);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid cost data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: ChangeCostPerDay.", e);
            throw new ControllerException("Database error. Command: ChangeCostPerDay.", e);
        }
    }
}
