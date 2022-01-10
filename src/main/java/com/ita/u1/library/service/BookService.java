package com.ita.u1.library.service;

import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;

import java.util.List;

public interface BookService {

    void add(Book book);

    List<Book> getAllBooks(int startFromBook, int amountOfBooks);

    int getNumberOfBooks();

    List<Book> findBook(String title);

    void changeCostPerDay(CopyBook copyBook);

    List<Book> findTheMostPopularBooks();

    Book findBookCover(int id);

}
