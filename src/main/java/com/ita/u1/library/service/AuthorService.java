package com.ita.u1.library.service;

import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.exception.ServiceException;

public interface AuthorService {

    void addAuthor(Author author) throws ServiceException;
    Author findAuthor(int id)throws ServiceException;

}
