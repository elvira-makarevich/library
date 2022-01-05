package com.ita.u1.library.service;

import com.ita.u1.library.dao.DAOProvider;
import com.ita.u1.library.service.impl.AuthorServiceImpl;
import com.ita.u1.library.service.impl.BookServiceImpl;
import com.ita.u1.library.service.impl.ClientServiceImpl;
import com.ita.u1.library.service.impl.OrderServiceImpl;
import com.ita.u1.library.service.validator.ServiceValidator;

public class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private DAOProvider daoProvider = DAOProvider.getInstance();

    private final ServiceValidator serviceValidator = new ServiceValidator();
    private final AuthorService authorService = new AuthorServiceImpl(daoProvider.getAuthorDAO(), serviceValidator);
    private final ClientService clientService = new ClientServiceImpl(daoProvider.getClientDAO(), serviceValidator);
    private final BookService bookService = new BookServiceImpl(daoProvider.getBookDAO(), serviceValidator);
    private final OrderService orderService = new OrderServiceImpl(daoProvider.getOrderDAO(), serviceValidator);

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
}

