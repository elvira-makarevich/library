package com.ita.u1.library.service;

import com.ita.u1.library.dao.DAOProvider;
import com.ita.u1.library.service.impl.AuthorServiceImpl;
import com.ita.u1.library.service.impl.BookServiceImpl;
import com.ita.u1.library.service.impl.ClientServiceImpl;

public class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private DAOProvider daoProvider = DAOProvider.getInstance();
//добавить и внедрить валидацию
    private final AuthorService authorService = new AuthorServiceImpl(daoProvider.getAuthorDAO());
    private final ClientService clientService = new ClientServiceImpl(daoProvider.getClientDAO());
    private final BookService bookService = new BookServiceImpl(daoProvider.getBookDAO());

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
}

