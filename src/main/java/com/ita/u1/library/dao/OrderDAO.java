package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.entity.Violation;

public interface OrderDAO {

    void saveOrder(Order order);

    boolean hasClientActiveOrder(Client client);

    Order findOrderInfo(Client client);

    void indicateBookViolation(Violation violation);
}
