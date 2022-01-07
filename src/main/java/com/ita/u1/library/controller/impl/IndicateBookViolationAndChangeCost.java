package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Violation;
import com.ita.u1.library.exception.ControllerException;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.exception.ServiceException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.ita.u1.library.util.ConstantParameter.*;

public class IndicateBookViolationAndChangeCost implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int orderId = Converter.toInt(request.getParameter(ORDER_ID));
        int copyId = Converter.toInt(request.getParameter(COPY_ID));
        String message = Validator.assertNotNullOrEmpty(request.getParameter(VIOLATION_MESSAGE));
        BigDecimal newCostPerDay = Converter.toBigDecimalOrNull(request.getParameter(NEW_COST_PER_DAY));
        List<byte[]> images = Converter.toListBytes(request.getParts().stream().filter(part -> IMAGES.equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList()));

        Violation violation = new Violation(orderId, copyId, message, images);
        CopyBook copyBook = new CopyBook(copyId, newCostPerDay);

        try {
            orderService.indicateBookViolation(violation);
            if (newCostPerDay != null) {
                bookService.changeCostPerDay(copyBook);
            }

        } catch (DAOConnectionPoolException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: IndicateBookViolationAndChangeCost.", e);
        } catch (DAOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: IndicateBookViolationAndChangeCost.", e);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("Invalid violation data.", e);
        }

    }
}
