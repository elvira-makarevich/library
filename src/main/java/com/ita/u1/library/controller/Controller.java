package com.ita.u1.library.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ita.u1.library.util.ConstantParameter.*;

@MultipartConfig
@WebServlet(asyncSupported = true)
public class Controller extends HttpServlet {

    private final CommandProvider provider = new CommandProvider();

    @Override
    public void init() {

        SimpleExecutorService simpleExecutorService = new SimpleExecutorService();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);

    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String commandName = request.getParameter(REQUEST_PARAM_COMMAND);
        Command command = provider.findCommand(commandName);
        command.execute(request, response);
    }

}
