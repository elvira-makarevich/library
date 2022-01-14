package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.EmailDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class EmailDAOImpl extends AbstractDAO implements EmailDAO {

    public EmailDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<Client> getClientsWithViolatedReturnDeadlineToday() {

        List<Client> clientWithViolation = new ArrayList<>();
        Client client = new Client();
        client.setEmail("elvira-makarevich@mail.ru");
        clientWithViolation.add(client);

        return clientWithViolation;
    }
}
