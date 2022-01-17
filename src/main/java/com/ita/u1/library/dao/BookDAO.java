package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    void add(Book book);

    List<Book> getAllBooks(int startFromBook, int amountOfBooks);

    int getNumberOfBooks();

    List<Book> findBook(String title);

    void changeCostPerDay(CopyBook copyBook);

    List<Book> findTheMostPopularBooks();

    Book findBookCover(int id);

    List<CopyBook> findBooksForWritingOff(String title);

    void writeBooksOff(List<CopyBook> copyBooks);

    boolean doesTheCopyBookExist(CopyBook copyBook);

}
