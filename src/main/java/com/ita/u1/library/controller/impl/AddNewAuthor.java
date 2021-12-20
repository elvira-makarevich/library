package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.commons.io.IOUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

public class AddNewAuthor implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Part filePart = request.getPart("file");
        InputStream inputStream = filePart.getInputStream();
        byte[] bytesImage = IOUtils.toByteArray(inputStream);

        Author author = new Author(firstName, lastName, bytesImage);

        try {
            authorService.addAuthor(author);
            response.sendRedirect("Controller?command=Go_To_Main_Page");
        } catch (DAOConnectionPoolException e) {
            //перевести на страницу с сообщением:проблемы доступа с соединением
            e.printStackTrace();
        } catch (DAOException e) {
            //перевести на страницу с сообщением: проблемы с созданием автора
            e.printStackTrace();
        }

    }
}
