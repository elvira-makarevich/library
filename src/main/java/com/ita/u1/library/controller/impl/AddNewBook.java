package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Genre;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddNewBook implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Author> authors = Converter.toListAuthors(request.getParameterValues("authorId"));
        String title = Validator.assertNotNullOrEmpty(request.getParameter("title"));
        String originalTitle = Converter.toNullIfEmpty(request.getParameter("originalTitle"));
        List<Genre> genres = Converter.toListGenres(request.getParameterValues("genres"));
        BigDecimal price = Converter.toBigDecimal(request.getParameter("price"));
        BigDecimal costPerDay = Converter.toBigDecimal(request.getParameter("costPerDay"));
        int numberOfCopies = Converter.toInt(request.getParameter("numberOfCopies"));
        int publishingYear = Converter.toNullIfEmptyOrInt(request.getParameter("publishingYear"));
        int numberOfPages = Converter.toNullIfEmptyOrInt(request.getParameter("numberOfPages"));
        List<byte[]> covers = Converter.toListBytes(request.getParts().stream().filter(part -> "covers".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList()));

        CopyBook[] copies = new CopyBook[numberOfCopies];
        for (int i = 0; i < numberOfCopies; i++) {
            copies[i] = new CopyBook(costPerDay, true);
        }

        Book book = new Book(title, originalTitle, genres, price, numberOfCopies, authors, covers, publishingYear, numberOfPages, copies);

        try {
            bookService.add(book);

        } catch (DAOConnectionPoolException e) {
            //перевести на страницу с сообщением:проблемы доступа с соединением
            e.printStackTrace();
        } catch (DAOException e) {
            //перевести на страницу с сообщением: проблемы с созданием книги
            e.printStackTrace();
        }


    }
}
