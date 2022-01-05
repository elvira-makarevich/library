package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.ServiceException;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.validator.ServiceValidator;

import java.util.Collections;
import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;
    private final ServiceValidator serviceValidator;

    public ClientServiceImpl(ClientDAO clientDAO, ServiceValidator serviceValidator) {
        this.clientDAO = clientDAO;
        this.serviceValidator = serviceValidator;
    }

    @Override
    public void add(Client client) {
        serviceValidator.validateClientRegistrationInfo(client);
        clientDAO.add(client);
    }

    @Override
    public boolean checkUniquenessPassportNumber(String passportNumber) {
        serviceValidator.validatePassportNumber(passportNumber);
        boolean result = clientDAO.checkUniquenessPassportNumber(passportNumber);
        return result;
    }

    @Override
    public boolean checkUniquenessEmail(String email) {
        serviceValidator.validateEmail(email);
        boolean result = clientDAO.checkUniquenessEmail(email);
        return result;
    }

    @Override
    public int getNumberOfClients() {

        int number = clientDAO.getNumberOfClients();
        if (number == 0) {
            throw new ServiceException("There are no clients in the library!");
        }
        return number;

    }

    @Override
    public List<Client> getAllClients(int startFromClient, int amountOfClients) {

        List<Client> clients = clientDAO.getAllClients(startFromClient, amountOfClients);
        if (clients.isEmpty() || clients == null) {
            return Collections.emptyList();
        }
        return clients;
    }

    @Override
    public List<Client> findClient(String lastName) {
        List<Client> clients = clientDAO.findClient(lastName);
        if (clients.isEmpty() || clients == null) {
            return Collections.emptyList();
        }
        return clients;
    }
}
