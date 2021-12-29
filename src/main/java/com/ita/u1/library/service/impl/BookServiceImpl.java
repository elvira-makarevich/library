package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.service.BookService;

import java.util.Comparator;
import java.util.List;


public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void add(Book book, CopyBook[] copies) {
        bookDAO.add(book, copies);
    }

    @Override
    public List<Book> getAllBooks(int startFromBook, int amountOfBooks) {

        List<Book> books = bookDAO.getAllBooks(startFromBook, amountOfBooks);
      //  books.sort(Comparator.comparing(Book::getNumberOfAvailableCopies).thenComparing(Book::getTitle));
        return books;
    }

    @Override
    public  int getNumberOfBooks() {

        int numberOfRecords = bookDAO.getNumberOfBooks();
        return numberOfRecords;
    }
}
