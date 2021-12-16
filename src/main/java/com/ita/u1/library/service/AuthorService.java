package com.ita.u1.library.service;

import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.exception.ServiceException;

import java.util.List;

public interface AuthorService {

    void addAuthor(Author author) throws ServiceException;
    List<Author> findAuthor(int id)throws ServiceException;

}
