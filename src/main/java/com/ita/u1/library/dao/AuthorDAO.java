package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Author;

import java.util.List;

public interface AuthorDAO {

    void addAuthor(Author author);
    List<Author> findAuthor (String lastName);
    Author findAuthorImage(int id);

}
