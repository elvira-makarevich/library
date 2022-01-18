package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.controller.util.Validator;
import com.ita.u1.library.entity.Address;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.*;
import com.ita.u1.library.service.ClientService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.ita.u1.library.util.ConstantParameter.*;

public class AddNewClient implements Command {

    private final ClientService clientService = ServiceProvider.getInstance().getClientService();
    private static final Logger log = LogManager.getLogger(AddNewClient.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = Validator.assertNotNullOrEmpty(request.getParameter(FIRST_NAME));
        String lastName = Validator.assertNotNullOrEmpty(request.getParameter(LAST_NAME));
        String patronymic = Converter.toNullIfEmpty(request.getParameter(PATRONYMIC));
        String passportNumber = Converter.toNullIfEmpty(request.getParameter(PASSPORT_NUMBER));
        String email = Validator.assertNotNullOrEmpty(request.getParameter(EMAIL));
        LocalDate dateOfBirth = Converter.toDate(request.getParameter(DATE_OF_BIRTH));
        int postcode = Converter.toInt(request.getParameter(POSTCODE));
        String country = Validator.assertNotNullOrEmpty(request.getParameter(COUNTRY));
        String locality = Validator.assertNotNullOrEmpty(request.getParameter(LOCALITY));
        String street = Validator.assertNotNullOrEmpty(request.getParameter(STREET));
        int houseNumber = Converter.toInt(request.getParameter(HOUSE_NUMBER));
        String building = Converter.toNullIfEmpty(request.getParameter(BUILDING));
        int apartmentNumber = Converter.toNullIfEmptyOrInt(request.getParameter(APARTMENT_NUMBER));
        byte[] bytesImage = Converter.toBytes(request.getPart(FILE));

        Address clientAddress = new Address(postcode, country, locality, street, houseNumber, building, apartmentNumber);
        Client client = new Client(firstName, lastName, patronymic, passportNumber, email, dateOfBirth, clientAddress, bytesImage);

        try {
            clientService.add(client);
        } catch (ControllerValidationException | ServiceException e) {
            log.error("Invalid client data.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DublicateEmailException e) {
            log.error("The client with the entered email is already registered!", e);
            throw new ControllerException("The client with the entered email is already registered!", e);
        } catch (DublicatePassportNumberException e) {
            log.error("The client with the entered passport number is already registered!", e);
            throw new ControllerException("The client with the entered passport number is already registered!", e);
        } catch (DAOConnectionPoolException | DAOException e) {
            log.error("Database error. Command: AddNewClient.", e);
            throw new ControllerException("Database error. Command: AddNewClient.", e);
        }
    }
}
