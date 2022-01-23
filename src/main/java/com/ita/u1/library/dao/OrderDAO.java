package com.ita.u1.library.dao;

import com.ita.u1.library.entity.*;

import java.util.List;

public interface OrderDAO {

    void saveOrder(Order order);

    boolean hasClientActiveOrder(int clientId);

    Order findOrderInfo(int clientId);

    void closeOrder(Order order);

    List<CopyBook> findCopyBookInfo(Order order);

    Profitability checkProfitability (Profitability profitabilityDates);
}
