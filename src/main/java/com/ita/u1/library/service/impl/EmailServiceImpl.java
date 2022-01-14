package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.EmailDAO;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.service.EmailService;

import java.util.List;

public class EmailServiceImpl implements EmailService {

    private final EmailDAO emailDAO;

    public EmailServiceImpl(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }

    @Override
    public List<Client> getClientsWithViolatedReturnDeadlineToday() {
        return emailDAO.getClientsWithViolatedReturnDeadlineToday();
    }
}
