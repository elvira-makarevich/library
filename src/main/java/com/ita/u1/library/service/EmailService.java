package com.ita.u1.library.service;

import com.ita.u1.library.entity.Client;

import java.util.List;

public interface EmailService {

    List<Client> getClientsWithViolatedReturnDeadlineToday();
}
