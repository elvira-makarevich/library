package com.ita.u1.library.dao;

import com.ita.u1.library.dao.exception.DAOException;
import com.ita.u1.library.entity.Author;

public interface AuthorDAO {

    void addAuthor(Author author) throws DAOException;
    Author findAuthor (int id) throws DAOException;
}
