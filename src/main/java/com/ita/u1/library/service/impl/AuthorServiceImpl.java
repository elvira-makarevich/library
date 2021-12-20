package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public void addAuthor(Author author) {

        authorDAO.addAuthor(author);

    }

    @Override
    public List<Author> findAuthor(String lastName) {

        List<Author> authors;
        authors = authorDAO.findAuthor(lastName);
        return authors;
    }

    @Override
    public Author findAuthorImage(int id) {

        Author author;
        author = authorDAO.findAuthorImage(id);
        return author;
    }
}
