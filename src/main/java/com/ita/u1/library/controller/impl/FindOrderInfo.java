package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Order;
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

public class FindOrderInfo extends AbstractCommand implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private static final Logger log = LogManager.getLogger(FindOrderInfo.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int clientId = Converter.toInt(request.getParameter(CLIENT_ID));

        try {
            Order order = orderService.findOrderInfo(clientId);
            sendResponseJSON(new Gson().toJson(order), response);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid client data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NoActiveOrderServiceException e) {
            log.error("Invalid order data: client does not have active order.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: FindClient.", e);
            throw new ControllerException("Database error. Command: FindClient.", e);
        }
    }
}
