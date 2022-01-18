package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.exception.ControllerException;
import com.ita.u1.library.exception.ControllerValidationException;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class GoToAllClientsPage implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();
    private static final Logger log = LogManager.getLogger(GoToAllClientsPage.class);

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int RECORDS_PER_PAGE = 20;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(CURRENT_PAGE) != null) {
            page = Converter.toInt(request.getParameter(CURRENT_PAGE));
        }

        try {
            int numberOfRecords = clientService.getNumberOfClients();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(CURRENT_PAGE, page);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.getRequestDispatcher(PATH_ALL_CLIENTS_PAGE).forward(request, response);
        } catch (ControllerValidationException e) {
            log.error("ControllerValidationException. Command: GoToAllClientsPage.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: GoToAllClientsPage.", e);
            throw new ControllerException("Database error. Command: GoToAllClientsPage.", e);
        }
    }
}
