package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.dao.exception.DAOException;
import com.ita.u1.library.dao.impl.AuthorDAOImpl;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.exception.ServiceException;

public class AuthorServiceImpl implements AuthorService {

    @Override
    public void addAuthor(Author author) throws ServiceException {

        AuthorDAO authorDAO = new AuthorDAOImpl();
        try {
        authorDAO.addAuthor(author);
        } catch (DAOException e) {
            // log
            throw new ServiceException(e);
        }

    }

    @Override
    public Author findAuthor(int id) throws ServiceException {
        AuthorDAO authorDAO = new AuthorDAOImpl();
        Author author;
        try {
            author= authorDAO.findAuthor(id);
        } catch (DAOException e) {
            // log
            throw new ServiceException(e);
        }
        return author;
    }
}
