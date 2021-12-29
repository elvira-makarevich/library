package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAllClientsPage implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();

    public static final String PATH_ALL_CLIENTS_PAGE = "/WEB-INF/jsp/allClients.jsp";
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
            page = Integer.parseInt(request.getParameter(PARAM_PAGE));
        }

        request.setAttribute(PARAM_PAGE, page);
        request.setAttribute(PARAM_NUMBER_OF_PAGES, numberOfPages);

        request.getRequestDispatcher(PATH_ALL_CLIENTS_PAGE).forward(request, response);

    }
}
