package com.ita.u1.library.dao;

import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.dao.connection_pool.ConnectionPoolImpl;
import com.ita.u1.library.dao.impl.*;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final AuthorDAO authorDAO = new AuthorDAOImpl(connectionPool);
    private final BookDAO bookDAO = new BookDAOImpl(connectionPool);
    private final ClientDAO clientDAO = new ClientDAOImpl(connectionPool);
    private final OrderDAO orderDAO = new OrderDAOImpl(connectionPool);
    private final EmailDAO emailDAO = new EmailDAOImpl(connectionPool);

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

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public EmailDAO getEmailDAO() {
        return emailDAO;
    }
}
