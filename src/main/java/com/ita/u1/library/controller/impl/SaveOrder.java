package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.CopyBook;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

public class SaveOrder implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private static final Logger log = LogManager.getLogger(SaveOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int clientId = Converter.toInt(request.getParameter(CLIENT_ID));
        BigDecimal preliminaryCost = Converter.toBigDecimal(request.getParameter(PRELIMINARY_COST));
        List<CopyBook> books = Converter.toListCopies(request.getParameterValues(COPY_ID));
        LocalDate orderDate = LocalDate.now();
        LocalDate possibleReturnDate = orderDate.plusMonths(1);
        Order order = new Order(clientId, preliminaryCost, books, orderDate, possibleReturnDate);

        try {
            orderService.saveOrder(order);
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: SaveOrder.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: SaveOrder.", e);
        } catch (DAOException e) {
            log.error("Database error. Command: SaveOrder.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: SaveOrder.", e);
        } catch (ServiceException e) {
            log.error("Invalid order data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("Invalid order data.", e);
        } catch (ActiveOrderServiceException e) {
            log.error("Client has active order.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("Client has active order.", e);
        }
    }
}
