package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.ControllerException;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.exception.ServiceException;
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
        } catch (DAOConnectionPoolException e) {
            log.error("Database connection error. Command: AddNewAuthor.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database connection error. Command: AddNewAuthor.", e);
        } catch (DAOException e) {
            log.error("Database error. Command: AddNewAuthor.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ControllerException("Database error. Command: AddNewAuthor.", e);
        } catch (ServiceException e) {
            log.error("Invalid author data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new ControllerException("Invalid author data.", e);
        }
    }
}
