package com.ita.u1.library.service;

import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.ViolationBook;

import java.util.List;

public interface BookService {

    void add(Book book);

    List<Book> getAllBooks(int startFromBook, int amountOfBooks);

    int getNumberOfBooks();

    List<CopyBook> findBooks(String title);

    void changeCostPerDay(CopyBook copyBook);

    void indicateBookViolation(ViolationBook violationBook);

    List<Book> findTheMostPopularBooks();

    Book findBookCover(int id);

    List<CopyBook> findBooksForWritingOff(String title);

    void writeBooksOff(List<CopyBook> copyBooks);

}
