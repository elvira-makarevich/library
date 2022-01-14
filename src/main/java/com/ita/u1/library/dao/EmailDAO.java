package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Client;

import java.util.List;

public interface EmailDAO {

    List<Client> getClientsWithViolatedReturnDeadlineToday();
}
