package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.exception.ServiceException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.validator.ServiceValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


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
        if (books.isEmpty()) {
            return Collections.emptyList();
        }
        return books;
    }

    @Override
    public int getNumberOfBooks() {
        return bookDAO.getNumberOfBooks();
    }

    @Override
    public List<CopyBook> findBooks(String title) {
        serviceValidator.validateTitle(title);
        List<CopyBook> copyBooks= bookDAO.findBooks(title);
        if (copyBooks.isEmpty()) {
            return Collections.emptyList();
        }
        return copyBooks;
    }

    @Override
    public void changeCostPerDay(CopyBook copyBook) {
        serviceValidator.validateCost(copyBook.getCostPerDay());
        if (!bookDAO.doesTheCopyBookExist(copyBook)) {
            throw new ServiceException("There is no such book in the library.");
        }
        bookDAO.changeCostPerDay(copyBook);
    }

    @Override
    public List<Book> findTheMostPopularBooks() {

        List<Book> books = bookDAO.findTheMostPopularBooks();
        if (books.isEmpty()) {
            return Collections.emptyList();
        }
        return books;
    }

    @Override
    public Book findBookCover(int id) {
        Book book = bookDAO.findBookCover(id);
        if (book == null) {
            return new Book();
        }
        return book;
    }

    @Override
    public List<CopyBook> findBooksForWritingOff(String title) {
        serviceValidator.validateTitle(title);
        List<CopyBook> copyBooks = bookDAO.findBooksForWritingOff(title);
        if (copyBooks.isEmpty() || copyBooks == null) {
            return Collections.emptyList();
        }
        return copyBooks;
    }

    @Override
    public void writeBooksOff(List<CopyBook> copyBooks) {

        serviceValidator.validateTheDuplicationOfCopyBooks(copyBooks);
        for (CopyBook copyBook : copyBooks) {
            if (!bookDAO.doesTheCopyBookExist(copyBook)) {
                throw new ServiceException("There is no such book in the library.");
            }
        }
        bookDAO.writeBooksOff(copyBooks);
    }
}
