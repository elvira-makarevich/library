package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.service.ClientService;

public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public void add(Client client) {

        clientDAO.add(client);

    }
}
