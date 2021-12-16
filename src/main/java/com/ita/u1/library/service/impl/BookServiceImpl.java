package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.service.BookService;


public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
}
