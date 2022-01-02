package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.Order;

public interface OrderDAO {

    void saveOrder(Order order);
    boolean hasClientActiveOrder(Client client);
}
