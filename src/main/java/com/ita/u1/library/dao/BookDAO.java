package com.ita.u1.library.dao;

import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;

public interface BookDAO {

    void add(Book book, CopyBook[] copies);
}
