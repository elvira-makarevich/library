package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.ActiveOrderServiceException;
import com.ita.u1.library.exception.MissingOrderServiceException;
import com.ita.u1.library.exception.NoActiveOrderServiceException;
import com.ita.u1.library.service.OrderService;
import com.ita.u1.library.service.validator.ServiceValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        List<CopyBook> copyBooks = orderDAO.findCopyBookInfo(order);
        serviceValidator.validateSaveOrder(order, copyBooks);
        orderDAO.saveOrder(order);
    }

    @Override
    public boolean hasClientActiveOrder(int clientId) {
        return orderDAO.hasClientActiveOrder(clientId);
    }

    @Override
    public Order findOrderInfo(int clientId) {
        Optional<Order> optionalOrder = orderDAO.findOrderInfo(clientId);
        Order order = optionalOrder.orElseThrow(() -> new NoActiveOrderServiceException("Client does not have active order."));
        return order;
    }

    @Override
    public void indicateBookViolation(Violation violation) {
        serviceValidator.validateViolationMessage(violation);
        if (!orderDAO.doesTheOrderExist(violation.getOrderId(), violation.getCopyId())) {
            throw new MissingOrderServiceException("The order does not exist!");
        }
        orderDAO.indicateBookViolation(violation);
    }

    @Override
    public void closeOrder(Order order) {
        Optional<Order> optionalOrder = orderDAO.findOrderInfo(order.getClientId());
        Order orderInfoFromDB = optionalOrder.orElseThrow(() -> new NoActiveOrderServiceException("Client does not have active order."));
        serviceValidator.validateCloseOrder(order, orderInfoFromDB);
        orderDAO.closeOrder(order);
    }

    @Override
    public Profitability checkProfitability(Profitability profitabilityDates) {
        //валидация дат
        Profitability profitability = orderDAO.checkProfitability(profitabilityDates);

        return profitability;
    }
}
