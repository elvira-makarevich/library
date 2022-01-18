package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

public class AddNewAuthor implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
    private static final Logger log = LogManager.getLogger(AddNewAuthor.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = Validator.assertNotNullOrEmpty(request.getParameter(FIRST_NAME));
        String lastName = Validator.assertNotNullOrEmpty(request.getParameter(LAST_NAME));
        byte[] bytesImage = Converter.toBytes(request.getPart(FILE));

        Author author = new Author(firstName, lastName, bytesImage);

        try {
            authorService.addAuthor(author);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid author data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: AddNewAuthor.", e);
            throw new ControllerException("Database error. Command: AddNewAuthor.", e);
        }
    }
}
