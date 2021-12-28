package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.service.BookService;

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
        return books;
    }

    @Override
    public int getNumberOfRecords() {

        int numberOfRecords = bookDAO.getNumberOfRecords();
        return numberOfRecords;
    }
}
