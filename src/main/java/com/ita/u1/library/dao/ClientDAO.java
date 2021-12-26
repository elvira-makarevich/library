package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Client;

public interface ClientDAO {

    void add(Client client);

    boolean checkUniquenessPassportNumber(String passportNumber);

    boolean checkUniquenessEmail(String email);

}
