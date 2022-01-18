package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class CheckClientActiveOrder extends AbstractCommand implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private static final Logger log = LogManager.getLogger(CheckClientActiveOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int clientId = Converter.toInt(request.getParameter(CLIENT_ID));

        try {
            boolean result = orderService.hasClientActiveOrder(clientId);
            sendResponseJSON(new Gson().toJson(result), response);
        } catch (ControllerValidationException e) {
            log.error("Invalid client id.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: CheckClientActiveOrder.", e);
            throw new ControllerException("Database error. Command: CheckClientActiveOrder.", e);
        }
    }
}
