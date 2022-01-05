package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SaveOrder implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int clientId = Converter.toInt(request.getParameter("clientId"));
        BigDecimal preliminaryCost = Converter.toBigDecimal(request.getParameter("preliminaryCost"));
        List<CopyBook> books = Converter.toListCopies(request.getParameterValues("copyId"));
        LocalDate orderDate = LocalDate.now();
        LocalDate possibleReturnDate = orderDate.plusMonths(1);
        Order order = new Order(clientId, preliminaryCost, books, orderDate, possibleReturnDate);

        orderService.saveOrder(order);

    }
}
