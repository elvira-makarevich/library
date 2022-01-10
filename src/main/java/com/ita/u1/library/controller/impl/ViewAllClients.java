package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

public class ViewAllClients implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();
    private static final Logger log = LogManager.getLogger(ViewAllClients.class);

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int RECORDS_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(CURRENT_PAGE) != null) {
            page = Converter.toInt(request.getParameter(CURRENT_PAGE));
        }

        try {
            int numberOfRecords = clientService.getNumberOfClients();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

            List<Client> clients = clientService.getAllClients((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);

            String json = new Gson().toJson(clients);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: ViewAllClients.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: ViewAllClients.", e);
        } catch (DAOException e) {
            log.error("Database error. Command: ViewAllClients.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: ViewAllClients.", e);
        } catch (MissingClientsServiceException e) {
            log.error("There are no clients in the library.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("There are no clients in the library.", e);
        }
    }
}
