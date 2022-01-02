package com.ita.u1.library.service;

import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.Order;

public interface OrderService {

    void saveOrder(Order order);
    boolean hasClientActiveOrder(Client client);
}
