package com.ita.u1.library.service;

import com.ita.u1.library.dao.DAOProvider;
import com.ita.u1.library.service.impl.*;
import com.ita.u1.library.service.validator.ServiceValidator;

public class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private DAOProvider daoProvider = DAOProvider.getInstance();

    private final ServiceValidator serviceValidator = new ServiceValidator();
    private final AuthorService authorService = new AuthorServiceImpl(daoProvider.getAuthorDAO(), serviceValidator);
    private final ClientService clientService = new ClientServiceImpl(daoProvider.getClientDAO(), serviceValidator);
    private final BookService bookService = new BookServiceImpl(daoProvider.getBookDAO(), serviceValidator);
    private final OrderService orderService = new OrderServiceImpl(daoProvider.getOrderDAO(), serviceValidator);
    private final MailService mailService = new MailServiceImpl(daoProvider.getMailDAO());

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public MailService getMailService() {
        return mailService;
    }
}

