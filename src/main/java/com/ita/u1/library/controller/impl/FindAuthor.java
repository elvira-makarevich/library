package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
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
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

public class FindAuthor extends AbstractCommand implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
    private static final Logger log = LogManager.getLogger(FindAuthor.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lastName = Validator.assertNotNullOrEmpty(request.getParameter(LAST_NAME));

        try {
            List<Author> authors = authorService.findAuthor(lastName);
            sendResponseJSON(new Gson().toJson(authors), response);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid last name.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: FindAuthor.", e);
            throw new ControllerException("Database error. Command: FindAuthor.", e);
        }
    }
}
