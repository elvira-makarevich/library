package com.ita.u1.library.service;

import com.ita.u1.library.entity.Author;

import java.util.List;

public interface AuthorService {

    void addAuthor(Author author);
    List<Author> findAuthor(String lastName);
    Author findAuthorImage(int id);


}
