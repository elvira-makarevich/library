package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.service.BookService;


public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void add(Book book, CopyBook[] copies) {
        bookDAO.add(book, copies);
    }
}
