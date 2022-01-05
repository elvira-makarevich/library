package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.NoSuchImageAuthorServiceException;
import com.ita.u1.library.service.AuthorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        List<Author> authors=authorDAO.findAuthor(lastName);

        if (authors.isEmpty()) {
            return Collections.emptyList();
        }

        return authors;
    }

    @Override
    public Author findAuthorImage(int id) {

        Optional<Author> optionalAuthor = authorDAO.findAuthorImage(id);
        Author author = optionalAuthor.orElseThrow(() -> new NoSuchImageAuthorServiceException("Image of the author with the id does not exist."));
        return author;
    }
}
