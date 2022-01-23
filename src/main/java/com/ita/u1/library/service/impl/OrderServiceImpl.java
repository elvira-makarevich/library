package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.ActiveOrderServiceException;
import com.ita.u1.library.exception.NoActiveOrderServiceException;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.validator.ServiceValidator;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ServiceValidator serviceValidator;

    public OrderServiceImpl(OrderDAO orderDAO, ServiceValidator serviceValidator) {
        this.orderDAO = orderDAO;
        this.serviceValidator = serviceValidator;
    }

    @Override
    public void saveOrder(Order order) {
        if (orderDAO.hasClientActiveOrder(order.getClientId())) {
            throw new ActiveOrderServiceException("Client has active order.");
        }
        List<CopyBook> copyBooksInfoFromDB = orderDAO.findCopyBookInfo(order);
        serviceValidator.validateSaveOrder(order, copyBooksInfoFromDB);
        orderDAO.saveOrder(order);
    }

    @Override
    public boolean hasClientActiveOrder(int clientId) {
        return orderDAO.hasClientActiveOrder(clientId);
    }

    @Override
    public Order findOrderInfo(int clientId) {
        Order order = orderDAO.findOrderInfo(clientId);
        if (order == null) {
            throw new NoActiveOrderServiceException("Order does not exist.");
        }
        order.setTotalCost(serviceValidator.calculateTotalCostBasedOnDataFromDB(order));
        return order;
    }

    @Override
    public void closeOrder(Order order) {
        Order orderInfoFromDB = orderDAO.findOrderInfo(order.getClientId());
        if (orderInfoFromDB == null) {
            throw new NoActiveOrderServiceException("Order does not exist.");
        }
        serviceValidator.validateCloseOrder(order, orderInfoFromDB);
        orderDAO.closeOrder(order);
    }

    @Override
    public Profitability checkProfitability(Profitability profitabilityDates) {
        serviceValidator.validateProfitabilityDates(profitabilityDates);
        Profitability profitability = orderDAO.checkProfitability(profitabilityDates);
        profitabilityDates.setFrom(profitabilityDates.getFrom().minusDays(1));
        profitabilityDates.setTo(profitabilityDates.getTo().plusDays(1));
        return profitability;
    }
}
