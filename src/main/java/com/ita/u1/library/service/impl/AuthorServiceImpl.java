package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.validator.ServiceValidator;

import java.util.Collections;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;
    private final ServiceValidator serviceValidator;

    public AuthorServiceImpl(AuthorDAO authorDAO, ServiceValidator serviceValidator) {
        this.authorDAO = authorDAO;
        this.serviceValidator = serviceValidator;
    }

    @Override
    public void addAuthor(Author author) {
        serviceValidator.validateAuthorRegistrationInfo(author);
        authorDAO.addAuthor(author);
    }

    @Override
    public List<Author> findAuthor(String lastName) {
        serviceValidator.validateLastName(lastName);
        List<Author> authors = authorDAO.findAuthor(lastName);
        if (authors.isEmpty()) {
            return Collections.emptyList();
        }
        return authors;
    }

}
