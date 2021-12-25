package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
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
import java.util.Date;

public class AddNewClient implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");//o
        String lastName = request.getParameter("lastName");//o
        String patronymic = request.getParameter("patronymic");
        String passportNumber = request.getParameter("passportNumber");
        String email = request.getParameter("email");//o
        String dateOfBirth = request.getParameter("dateOfBirth");//o
        String postcode = request.getParameter("postcode");//o
        String country = request.getParameter("country");//o
        String locality = request.getParameter("locality");//o
        String street = request.getParameter("street");//o
        String houseNumber = request.getParameter("houseNumber");//o
        String building = request.getParameter("building");
        String apartmentNumber = request.getParameter("apartmentNumber");

        System.out.println(dateOfBirth);
        int postcodeClient = Integer.parseInt(postcode);
        int houseNumberClient = Integer.parseInt(houseNumber);
        int apartmentNumberClient = Integer.parseInt(apartmentNumber);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirthClient = null;
        try {
            dateOfBirthClient = df.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Part filePart = request.getPart("file");
        InputStream inputStream = filePart.getInputStream();
        byte[] bytesImage = IOUtils.toByteArray(inputStream);

        Address clientAddress = new Address(postcodeClient, country, locality, street, houseNumberClient, building, apartmentNumberClient);

        Client client = new Client(firstName, lastName, patronymic, passportNumber, email, dateOfBirthClient, clientAddress, bytesImage);

        clientService.add(client);

    }
}
