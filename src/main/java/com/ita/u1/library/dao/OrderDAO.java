package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.entity.Violation;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    void saveOrder(Order order);

    boolean hasClientActiveOrder(int clientId);

    Optional<Order> findOrderInfo(int clientId);

    void indicateBookViolation(Violation violation);

    void closeOrder(Order order);

    List<CopyBook> findCopyBookInfo(Order order);
}
