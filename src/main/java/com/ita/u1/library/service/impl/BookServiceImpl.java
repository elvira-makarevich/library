package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.exception.ServiceException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.validator.ServiceValidator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final ServiceValidator serviceValidator;

    public BookServiceImpl(BookDAO bookDAO, ServiceValidator serviceValidator) {
        this.bookDAO = bookDAO;
        this.serviceValidator = serviceValidator;
    }

    @Override
    public void add(Book book) {
        serviceValidator.validateBookRegistrationInfo(book);
        bookDAO.add(book);
    }

    @Override
    public List<Book> getAllBooks(int startFromBook, int amountOfBooks) {

        List<Book> books = bookDAO.getAllBooks(startFromBook, amountOfBooks);
        //  books.sort(Comparator.comparing(Book::getNumberOfAvailableCopies).thenComparing(Book::getTitle));
        if (books.isEmpty() || books == null) {
            throw new ServiceException("There are no books in the library!");
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
        serviceValidator.validateTitle(title);
        List<Book> books = bookDAO.findBook(title);
        if (books.isEmpty() || books == null) {
            return Collections.emptyList();
        }
        return books;
    }

    @Override
    public void changeCostPerDay(CopyBook copyBook) {
        serviceValidator.validateCost(copyBook.getCostPerDay());
        bookDAO.changeCostPerDay(copyBook);
    }
}
