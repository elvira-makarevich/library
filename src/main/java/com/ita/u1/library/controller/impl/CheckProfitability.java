package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Profitability;
import com.ita.u1.library.exception.ControllerException;
import com.ita.u1.library.exception.ControllerValidationException;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.ita.u1.library.util.ConstantParameter.*;

public class CheckProfitability extends AbstractCommand implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private static final Logger log = LogManager.getLogger(CheckProfitability.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LocalDate from = Converter.toDate(request.getParameter(FROM)).minusDays(1);
        LocalDate to = Converter.toDate(request.getParameter(TO)).plusDays(1);
        Profitability profitabilityDates = new Profitability(from, to);

        try {
            Profitability profitability = orderService.checkProfitability(profitabilityDates);
            sendResponseJSON(new Gson().toJson(profitability), response);
        } catch (ControllerValidationException e) {
            log.error("Invalid dates.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: CheckProfitability.", e);
            throw new ControllerException("Database error. Command: CheckProfitability.", e);
        }
    }
}
