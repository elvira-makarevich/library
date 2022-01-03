package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.entity.Violation;
import com.ita.u1.library.service.OrderService;

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
        return orderDAO.findOrderInfo(client);
    }

    @Override
    public void indicateBookViolation(Violation violation) {
        orderDAO.indicateBookViolation(violation);
    }


}
