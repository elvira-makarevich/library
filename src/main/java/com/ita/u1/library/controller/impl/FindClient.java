package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.DAOConnectionPoolException;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindClient implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lastName = request.getParameter("lastName");
        lastName.trim();
        if (lastName == null || lastName.isEmpty()) {
            //+message
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            List<Client> clients = clientService.findClient(lastName);
            String json = new Gson().toJson(clients);
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);

        } catch (DAOConnectionPoolException e) {
            //перевести на страницу с сообщением:проблемы доступа к бд
            e.printStackTrace();
        } catch (
                DAOException e) {
            //перевести на страницу с сообщением: проблемы при запросе информации из бд
            e.printStackTrace();
        }
    }
}