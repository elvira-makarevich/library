package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Validator;
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

public class FindClient extends AbstractCommand implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();
    private static final Logger log = LogManager.getLogger(FindClient.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lastName = Validator.assertNotNullOrEmpty(request.getParameter(LAST_NAME));

        try {
            List<Client> clients = clientService.findClient(lastName);
            sendResponseJSON(new Gson().toJson(clients), response);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid client data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: FindClient.", e);
            throw new ControllerException("Database error. Command: FindClient.", e);
        }
    }
}