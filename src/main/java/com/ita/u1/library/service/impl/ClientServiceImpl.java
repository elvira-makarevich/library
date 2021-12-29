package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.service.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public void add(Client client) {

        clientDAO.add(client);

    }

    @Override
    public boolean checkUniquenessPassportNumber(String passportNumber) {

        boolean result = clientDAO.checkUniquenessPassportNumber(passportNumber);
        return result;
    }

    @Override
    public boolean checkUniquenessEmail(String email) {

        boolean result = clientDAO.checkUniquenessEmail(email);
        return result;
    }

    @Override
    public int getNumberOfClients() {

        int number = clientDAO.getNumberOfClients();
        return number;

    }

    @Override
    public List<Client> getAllClients(int startFromClient, int amountOfClients) {

        List<Client> clients = clientDAO.getAllClients(startFromClient, amountOfClients);

        return clients;
    }
}
