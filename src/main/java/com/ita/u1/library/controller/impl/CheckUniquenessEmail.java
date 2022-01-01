package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckUniquenessEmail implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = Validator.assertNotNullOrEmpty(request.getParameter("email"));

        try {
            boolean result = clientService.checkUniquenessEmail(email);

            String json = new Gson().toJson(result);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);
        } catch (DAOConnectionPoolException e) {

            e.printStackTrace();
        } catch (DAOException e) {

            e.printStackTrace();
        }
    }

}
