package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {

    void addAuthor(Author author);

    List<Author> findAuthor(String lastName);

    Optional<Author> findAuthorImage(int id);

}
