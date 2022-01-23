package com.ita.u1.library.service;

import com.ita.u1.library.entity.*;


public interface OrderService {

    void saveOrder(Order order);

    boolean hasClientActiveOrder(int clientId);

    Order findOrderInfo(int clientId);

    void closeOrder(Order order);

    Profitability checkProfitability (Profitability profitabilityDates);
}
