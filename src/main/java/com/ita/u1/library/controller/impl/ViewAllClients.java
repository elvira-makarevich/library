package com.ita.u1.library.controller.impl;

import com.google.gson.Gson;
import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewAllClients implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();

    public static final String PARAM_PAGE = "currentPage";
    public static final String PARAM_NUMBER_OF_PAGES = "numberOfPages";

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int RECORDS_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int numberOfRecords = clientService.getNumberOfClients();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        int page = DEFAULT_PAGE_NUMBER;

        if (request.getParameter(PARAM_PAGE) != null) {
            page = Converter.toInt(request.getParameter(PARAM_PAGE));
        }

        List<Client> clients = clientService.getAllClients((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        request.setAttribute(PARAM_NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(PARAM_PAGE, page);

        String json = new Gson().toJson(clients);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().write(json);

    }
}
