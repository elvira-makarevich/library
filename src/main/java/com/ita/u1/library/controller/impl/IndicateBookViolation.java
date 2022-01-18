package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Violation;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ita.u1.library.util.ConstantParameter.*;

public class IndicateBookViolation implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private static final Logger log = LogManager.getLogger(IndicateBookViolation.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int orderId = Converter.toInt(request.getParameter(ORDER_ID));
        int copyId = Converter.toInt(request.getParameter(COPY_ID));
        String message = Validator.assertNotNullOrEmpty(request.getParameter(VIOLATION_MESSAGE));
        List<byte[]> images = Converter.toListBytes(request.getParts().stream().filter(part -> IMAGES.equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList()));

        Violation violation = new Violation(orderId, copyId, message, images);

        try {
            orderService.indicateBookViolation(violation);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid violation data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (MissingOrderServiceException e) {
            log.error("Order with the specified book does not exist.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: IndicateBookViolation.", e);
            throw new ControllerException("Database error. Command: IndicateBookViolation.", e);
        }
    }
}
