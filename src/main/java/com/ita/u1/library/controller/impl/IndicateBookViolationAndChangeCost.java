package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Violation;
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

public class IndicateBookViolationAndChangeCost implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();
    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int orderId = Converter.toInt(request.getParameter("orderId"));
        int copyId = Converter.toInt(request.getParameter("copyId"));
        String message = Validator.assertNotNullOrEmpty(request.getParameter("violationMessage"));
        BigDecimal newCostPerDay = Converter.toBigDecimalOrNull(request.getParameter("newCostPerDay"));
        List<byte[]> images = Converter.toListBytes(request.getParts().stream().filter(part -> "images".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList()));

        Violation violation = new Violation(orderId, copyId, message, images);
        CopyBook copyBook = new CopyBook(copyId, newCostPerDay);

        orderService.indicateBookViolation(violation);
        if (newCostPerDay != null) {
            bookService.changeCostPerDay(copyBook);
        }

    }
}
