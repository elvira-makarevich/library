package com.ita.u1.library.service;

import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;

public interface BookService {

    void add(Book book, CopyBook [] copies);

}
