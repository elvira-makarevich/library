package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
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

        String[] authorsId = request.getParameterValues("authorId");//обяз
        String title = request.getParameter("title");//обяз
        String originalTitle = request.getParameter("originalTitle");
        String[] genres = request.getParameterValues("genres");//обяз
        String price = request.getParameter("price");//обяз
        String costPerDay = request.getParameter("costPerDay");//обяз
        String numberOfCopies = request.getParameter("numberOfCopies");//обяз
        String publishingYear = request.getParameter("publishingYear");
        String numberOfPages = request.getParameter("numberOfPages");
        List<Part> fileParts = request.getParts().stream().filter(part -> "covers".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());

        //валидация
        if (authorsId.length == 0 || title == null || title.isEmpty() || genres.length == 0 || price == null || price.isEmpty() || costPerDay == null || costPerDay.isEmpty() || numberOfCopies == null || numberOfCopies.isEmpty() || fileParts.isEmpty()) {
            response.sendRedirect("");//можно на главную...но лучше вернуться+сообщение проверьте правильность введенных данных
        }

        List<Author> authors = new ArrayList<>();
        for (String s : authorsId) {
            authors.add(new Author(Integer.parseInt(s)));
        }

        List<Genre> genreList = new ArrayList<>();
        for (String s : genres) {
            genreList.add(Genre.valueOf(s.toUpperCase()));
        }

        BigDecimal bookPrice = new BigDecimal(price.replace(',', '.'));
        BigDecimal bookCostPerDay = new BigDecimal(costPerDay.replace(',', '.'));

        int bookPublishingYear = 0;
        if (publishingYear != null && !publishingYear.isEmpty()) {
            bookPublishingYear = Integer.parseInt(publishingYear);
        }

        int bookNumberOfPages = 0;
        if (numberOfPages != null && !numberOfPages.isEmpty()) {
            bookNumberOfPages = Integer.parseInt(numberOfPages);
        }

        List<byte[]> covers = new ArrayList<>();

        for (Part filePart : fileParts) {
            InputStream inputStream = filePart.getInputStream();
            covers.add(IOUtils.toByteArray(inputStream));
        }

        int copiesNumber = Integer.parseInt(numberOfCopies);
        CopyBook[] copies = new CopyBook[copiesNumber];
        for (int i = 0; i < copiesNumber; i++) {
            copies[i] = new CopyBook(bookCostPerDay, true);
        }

        Book book = new Book(title, originalTitle, genreList, bookPrice, copiesNumber, authors, covers, bookPublishingYear, bookNumberOfPages, copies);

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
