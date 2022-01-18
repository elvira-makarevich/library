package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Validator;
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

public class CheckUniquenessPassportNumber extends AbstractCommand implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();
    private static final Logger log = LogManager.getLogger(CheckUniquenessPassportNumber.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passportNumber = Validator.assertNotNullOrEmpty(request.getParameter(PASSPORT_NUMBER));

        try {
            boolean result = clientService.checkUniquenessPassportNumber(passportNumber);
            sendResponseJSON(new Gson().toJson(result), response);
        } catch (ControllerValidationException e) {
            log.error("Invalid passport number.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: CheckUniquenessPassportNumber.", e);
            throw new ControllerException("Database error. Command: CheckUniquenessPassportNumber.", e);
        }
    }
}
