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

public class CloseOrder implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private static final Logger log = LogManager.getLogger(CloseOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int orderId = Converter.toInt(request.getParameter(ORDER_ID));
        int clientId = Converter.toInt(request.getParameter(CLIENT_ID));
        LocalDate orderDate = Converter.toDate(request.getParameter(ORDER_DATE));
        LocalDate possibleReturnDate = Converter.toDate(request.getParameter(POSSIBLE_RETURN_DATE));
        BigDecimal preliminaryCost = Converter.toBigDecimal(request.getParameter(PRELIMINARY_COST));
        BigDecimal penalty = Converter.toBigDecimalOrNull(request.getParameter(PENALTY));
        BigDecimal totalCost = Converter.toBigDecimal(request.getParameter(TOTAL_COST));
        LocalDate realReturnDate = LocalDate.now();

        List<CopyBook> books = Converter.toListCopies(request.getParameterValues(COPY_ID));
        for (int i = 0; i < books.size(); i++) {
            String rating = RATING + i;
            double ratingBook = Converter.toNullIfEmptyOrInt(request.getParameter(rating));
            books.get(i).setRating(ratingBook);
        }

        Order order = new Order(orderId, clientId, books, orderDate, possibleReturnDate, realReturnDate, preliminaryCost, penalty, totalCost);

        try {
            orderService.closeOrder(order);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid order data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NoActiveOrderServiceException e) {
            log.error("Invalid order data: client does not have active order.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: CloseOrder.", e);
            throw new ControllerException("Database error. Command: CloseOrder.", e);
        }
    }
}
