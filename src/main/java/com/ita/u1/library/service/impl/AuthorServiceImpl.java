package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.dao.exception.DAOException;
import com.ita.u1.library.dao.impl.AuthorDAOImpl;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.exception.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO=authorDAO;
    }

    @Override
    public void addAuthor(Author author) throws ServiceException {


        try {
        authorDAO.addAuthor(author);
        } catch (DAOException e) {
            // log
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Author> findAuthor(int id) throws ServiceException {
        AuthorDAO authorDAO = new AuthorDAOImpl();
        List<Author> authors;
        try {
            authors= authorDAO.findAuthor(id);
        } catch (DAOException e) {
            // log
            throw new ServiceException(e);
        }
        return authors;
    }
}
