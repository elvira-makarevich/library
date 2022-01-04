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

public class CloseOrder implements Command {

    private final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int orderId = Converter.toInt(request.getParameter("orderId"));
        LocalDate orderDate = Converter.toDate(request.getParameter("orderDate"));
        LocalDate possibleReturnDate = Converter.toDate(request.getParameter("possibleReturnDate"));
        BigDecimal preliminaryCost = Converter.toBigDecimal(request.getParameter("preliminaryCost"));
        BigDecimal penalty = Converter.toBigDecimalOrNull(request.getParameter("penalty"));
        BigDecimal totalCost = Converter.toBigDecimal(request.getParameter("totalCost"));
        LocalDate realReturnDate = LocalDate.now();

        List<CopyBook> books = Converter.toListCopies(request.getParameterValues("copyId"));
        for (int i = 0; i < books.size(); i++) {
            String rating = "rating" + i;
            double ratingBook = Converter.toNullIfEmptyOrInt(request.getParameter(rating));
            books.get(i).setRating(ratingBook);
        }

        Order order = new Order(orderId, books, orderDate, possibleReturnDate, realReturnDate, preliminaryCost, penalty, totalCost);
        orderService.closeOrder(order);
    }
}
