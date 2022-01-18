package com.ita.u1.library.dao;

import com.ita.u1.library.entity.*;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    void saveOrder(Order order);

    boolean hasClientActiveOrder(int clientId);

    Order findOrderInfo(int clientId);

    void indicateBookViolation(Violation violation);

    void closeOrder(Order order);

    List<CopyBook> findCopyBookInfo(Order order);

    boolean doesTheOrderExist(int orderId, int copyId);

    Profitability checkProfitability (Profitability profitabilityDates);
}
