package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.exception.ControllerException;
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

public class CheckUniquenessPassportNumber implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();
    private static final Logger log = LogManager.getLogger(CheckUniquenessPassportNumber.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passportNumber = Validator.assertNotNullOrEmpty(request.getParameter(PASSPORT_NUMBER));

        try {
            boolean result = clientService.checkUniquenessPassportNumber(passportNumber);

            String json = new Gson().toJson(result);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: CheckUniquenessPassportNumber.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: CheckUniquenessPassportNumber.", e);
        } catch (DAOException e) {
            log.error("Database connection error. Command:  CheckUniquenessPassportNumber.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: CheckUniquenessPassportNumber.", e);
        }
    }
}
