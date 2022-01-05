package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.entity.Violation;
import com.ita.u1.library.exception.NoActiveOrderServiceException;
import com.ita.u1.library.exception.NoSuchImageAuthorServiceException;
import com.ita.u1.library.service.OrderService;

import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    @Override
    public boolean hasClientActiveOrder(Client client) {
        return orderDAO.hasClientActiveOrder(client);
    }

    @Override
    public Order findOrderInfo(Client client) {

        Optional<Order> optionalOrder = orderDAO.findOrderInfo(client);
        Order order = optionalOrder.orElseThrow(() -> new NoActiveOrderServiceException("Client does not have active order."));
        return order;

    }

    @Override
    public void indicateBookViolation(Violation violation) {
        orderDAO.indicateBookViolation(violation);
    }

    @Override
    public void closeOrder(Order order) {
        orderDAO.closeOrder(order);
    }


}
