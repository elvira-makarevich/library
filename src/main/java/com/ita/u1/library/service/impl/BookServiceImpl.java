package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.exception.ServiceException;
import com.ita.u1.library.service.BookService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void add(Book book) {
        bookDAO.add(book);
    }

    @Override
    public List<Book> getAllBooks(int startFromBook, int amountOfBooks) {

        List<Book> books = bookDAO.getAllBooks(startFromBook, amountOfBooks);
        //  books.sort(Comparator.comparing(Book::getNumberOfAvailableCopies).thenComparing(Book::getTitle));
        if (books.isEmpty()) {
            return Collections.emptyList();
        }

        return books;
    }

    @Override
    public int getNumberOfBooks() {

        int numberOfRecords = bookDAO.getNumberOfBooks();
        if (numberOfRecords == 0) {
            throw new ServiceException("There are no books in the library!");
        }
        return numberOfRecords;
    }

    @Override
    public List<Book> findBook(String title) {

        List<Book> books = bookDAO.findBook(title);
        if (books.isEmpty()) {
            return Collections.emptyList();
        }
        return books;
    }

    @Override
    public void changeCostPerDay(CopyBook copyBook) {
        bookDAO.changeCostPerDay(copyBook);
    }
}
