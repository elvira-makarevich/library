package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.entity.Violation;

import java.util.Optional;

public interface OrderDAO {

    void saveOrder(Order order);

    boolean hasClientActiveOrder(Client client);

    Optional<Order> findOrderInfo(Client client);

    void indicateBookViolation(Violation violation);

    void closeOrder(Order order);
}
