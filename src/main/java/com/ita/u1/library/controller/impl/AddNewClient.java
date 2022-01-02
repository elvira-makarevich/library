package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Address;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddNewClient implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = Validator.assertNotNullOrEmpty(request.getParameter("firstName"));
        String lastName = Validator.assertNotNullOrEmpty(request.getParameter("lastName"));
        String patronymic = Converter.toNullIfEmpty(request.getParameter("patronymic"));
        String passportNumber = Converter.toNullIfEmpty(request.getParameter("passportNumber"));
        String email = Validator.assertNotNullOrEmpty(request.getParameter("email"));
        LocalDate dateOfBirth = Converter.toDate(request.getParameter("dateOfBirth"));
        int postcode = Converter.toInt(request.getParameter("postcode"));
        String country = Validator.assertNotNullOrEmpty(request.getParameter("country"));
        String locality = Validator.assertNotNullOrEmpty(request.getParameter("locality"));
        String street = Validator.assertNotNullOrEmpty(request.getParameter("street"));
        int houseNumber = Converter.toInt(request.getParameter("houseNumber"));
        String building = Converter.toNullIfEmpty(request.getParameter("building"));
        int apartmentNumber = Converter.toNullIfEmptyOrInt(request.getParameter("apartmentNumber"));
        byte[] bytesImage = Converter.toBytes(request.getPart("file"));

        Address clientAddress = new Address(postcode, country, locality, street, houseNumber, building, apartmentNumber);
        Client client = new Client(firstName, lastName, patronymic, passportNumber, email, dateOfBirth, clientAddress, bytesImage);

        clientService.add(client);

    }
}
