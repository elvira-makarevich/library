package com.ita.u1.library.dao;

import com.ita.u1.library.dao.exception.DAOException;
import com.ita.u1.library.entity.Author;

import java.util.List;

public interface AuthorDAO {

    void addAuthor(Author author) throws DAOException;
    List<Author> findAuthor (int id) throws DAOException;
}
