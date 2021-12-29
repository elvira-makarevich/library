package com.ita.u1.library.service;

import com.ita.u1.library.entity.Client;

import java.util.List;

public interface ClientService {

    void add(Client client);

    boolean checkUniquenessPassportNumber(String passportNumber);

    boolean checkUniquenessEmail(String email);

    int getNumberOfClients();

    List<Client> getAllClients(int startFromClient, int amountOfClients);
}
