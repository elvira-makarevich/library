package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Genre;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.BookService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.ita.u1.library.util.ConstantParameter.*;

public class AddNewBook implements Command {

    private final BookService bookService = ServiceProvider.getInstance().getBookService();
    private static final Logger log = LogManager.getLogger(AddNewBook.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Author> authors = Converter.toListAuthors(request.getParameterValues(AUTHOR_ID));
        String title = Validator.assertNotNullOrEmpty(request.getParameter(TITLE));
        String originalTitle = Converter.toNullIfEmpty(request.getParameter(ORIGINAL_TITLE));
        List<Genre> genres = Converter.toListGenres(request.getParameterValues(GENRES));
        BigDecimal price = Converter.toBigDecimal(request.getParameter(PRICE));
        BigDecimal costPerDay = Converter.toBigDecimal(request.getParameter(COST_PER_DAY));
        int numberOfCopies = Converter.toInt(request.getParameter(NUMBER_OF_COPIES));
        int publishingYear = Converter.toNullIfEmptyOrInt(request.getParameter(PUBLISHING_YEAR));
        int numberOfPages = Converter.toNullIfEmptyOrInt(request.getParameter(NUMBER_OF_PAGES));
        List<byte[]> covers = Converter.toListBytes(request.getParts().stream().filter(part -> COVERS.equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList()));

        CopyBook[] copies = new CopyBook[numberOfCopies];
        for (int i = 0; i < numberOfCopies; i++) {
            copies[i] = new CopyBook(costPerDay);
        }

        Book book = new Book(title, originalTitle, genres, price, numberOfCopies, authors, covers, publishingYear, numberOfPages, copies);

        try {
            bookService.add(book);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid book data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: AddNewBook.", e);
            throw new ControllerException("Database error. Command: AddNewBook.", e);
        }
    }
}
