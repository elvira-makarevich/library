package com.ita.u1.library.service;

import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;

import java.util.List;

public interface BookService {

    void add(Book book, CopyBook [] copies);
    List<Book> getAllBooks(int startFromBook, int amountOfBooks);
    int getNumberOfRecords();

}
