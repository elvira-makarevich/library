package com.ita.u1.library.service;

import com.ita.u1.library.entity.Client;

public interface ClientService {

    void add(Client client);
    boolean checkUniquenessPassportNumber(String passportNumber);
    boolean checkUniquenessEmail(String email);
}
