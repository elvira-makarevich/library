package com.ita.u1.library.dao;

import com.ita.u1.library.dao.impl.AuthorDAOImpl;
import com.ita.u1.library.dao.impl.BookDAOImpl;
import com.ita.u1.library.dao.impl.ClientDAOImpl;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();
    private final AuthorDAO authorDAO = new AuthorDAOImpl();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final ClientDAO clientDAO = new ClientDAOImpl();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }
}
