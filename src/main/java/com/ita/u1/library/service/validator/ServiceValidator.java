package com.ita.u1.library.service.validator;

import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ServiceValidator {

    public static final String PATTERN_FIRST_NAME_LAST_NAME = "([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$)";
    public static final String PATTERN_PATRONYMIC = "([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$)|(^\\s*$)";
    public static final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$";
    public static final String PATTERN_PASSPORT_NUMBER = "([A-Z]{2}[0-9]{7}$)|(^\\s*$)";
    public static final String PATTERN_POST_CODE = "^[0-9]{6}$";
    public static final String PATTERN_COUNTRY_LOCALITY_STREET = "([a-zA-Z ]{2,40}$)|(^[\\p{L} ]{2,40}$)";
    public static final String PATTERN_HOUSE_NUMBER = "^[0-9]{1,3}$";
    public static final String PATTERN_BUILDING = "(^[0-9A-Za-z]$)|(^\\s*$)";
    public static final String PATTERN_APARTMENT_NUMBER = "^[0-9]{1,4}$|(^\\s*$)";

    public static final String PATTERN_TITLE = ".{2,70}$";
    public static final String PATTERN_ORIGINAL_TITLE = "(.{2,70}$)|(^\\s*$)";
    public static final String PATTERN_COST = "^[0-9]{1,}[.,]?[0-9]{0,2}";


    public void validateBookRegistrationInfo(Book book) {
        boolean result = checkTitle(book.getTitle()) &&
                checkOriginalTitle(book.getOriginalTitle()) &&
                checkCost(book.getPrice()) &&
                checkNumberOfCopies(book.getNumberOfCopies()) &&
                checkCost(book.getCopies()[0].getCostPerDay()) &&
                checkPublishingYear(book.getPublishingYear()) &&
                checkNumberOfPages(book.getNumberOfPages());
        if (!result) {
            throw new ServiceException("Invalid book data.");
        }
    }

    public void validateAuthorRegistrationInfo(Author author) {
        boolean result = checkFirstLastName(author.getFirstName()) &&
                checkFirstLastName(author.getLastName());
        if (!result) {
            throw new ServiceException("Invalid author data.");
        }
    }

    public void validateClientRegistrationInfo(Client client) {
        boolean result = checkFirstLastName(client.getFirstName()) &&
                checkFirstLastName(client.getLastName()) &&
                checkPatronymic(client.getPatronymic()) &&
                checkEmail(client.getEmail()) &&
                checkPassportNumber(client.getPassportNumber()) &&
                checkPostCode(client.getAddress().getPostcode()) &&
                checkCountryLocalityStreet(client.getAddress().getCountry()) &&
                checkCountryLocalityStreet(client.getAddress().getLocality()) &&
                checkCountryLocalityStreet(client.getAddress().getStreet()) &&
                checkHouseNumber(client.getAddress().getHouseNumber()) &&
                checkHouseBuilding(client.getAddress().getBuilding()) &&
                checkApartmentNumber(client.getAddress().getApartmentNumber()) &&
                checkDateOfBirth(client.getDateOfBirth());

        if (!result) {
            throw new ServiceException("Invalid client data.");
        }
    }

    public void validatePassportNumber(String passportNumber) {
        boolean result = checkPassportNumber(passportNumber);
        if (!result) {
            throw new ServiceException("Invalid passport number.");
        }

    }

    public void validateEmail(String email) {
        boolean result = checkEmail(email);
        if (!result) {
            throw new ServiceException("Invalid email.");
        }
    }

    public void validateLastName(String lastName) {
        boolean result = checkFirstLastName(lastName);
        if (!result) {
            throw new ServiceException("Invalid last name.");
        }
    }

    public void validateCost(BigDecimal cost) {
        boolean result = checkCost(cost);
        if (!result) {
            throw new ServiceException("Invalid cost.");
        }
    }

    public void validateSaveOrder(Order order, List<CopyBook> copyBooks) {

        checkTheDuplicationOfBooks(copyBooks);

        int numberOfBooks = order.getBooks().size();
        if (numberOfBooks > 5) {
            throw new ServiceException("Invalid order. The maximum number of books in an order is 5.");
        }

        BigDecimal preCost = calculateTheCostOfTheOrder(order, copyBooks, numberOfBooks);
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        double d1 = preCost.doubleValue();
        double d2 = order.getPreliminaryCost().doubleValue();
        double difference = Math.abs(d1 - d2);
        System.out.println(difference);

        if (difference > 0.3) {
            throw new ServiceException("Invalid order.");
        }

    }

    private void checkTheDuplicationOfBooks(List<CopyBook> copyBooks) {
        for (int i = 0; i < copyBooks.size(); i++) {
            int bookId = copyBooks.get(i).getBookId();
            for (int j = 0; j < copyBooks.size(); j++) {
                if (i == j) continue;
                if (bookId == copyBooks.get(j).getBookId()) {
                    throw new ServiceException("The order contains the same books.");
                }
            }
        }
    }

    private BigDecimal calculateTheCostOfTheOrder(Order order, List<CopyBook> copyBooks, int numberOfBooks) {

        long daysNumber = order.getPossibleReturnDate().toEpochDay() - order.getOrderDate().toEpochDay() + 1;

        BigDecimal daysRentNumber = new BigDecimal(daysNumber);
        BigDecimal maxDiscount = new BigDecimal(0.85);
        BigDecimal discount = new BigDecimal(0.9);
        BigDecimal dayCostAllBooksWithoutDiscount = new BigDecimal(0);

        for (int i = 0; i < copyBooks.size(); i++) {
            dayCostAllBooksWithoutDiscount = dayCostAllBooksWithoutDiscount.add(copyBooks.get(i).getCostPerDay());
        }

        BigDecimal preCost = null;
        if (numberOfBooks > 4) {
            preCost = dayCostAllBooksWithoutDiscount.multiply(daysRentNumber).multiply(maxDiscount);
        } else if (numberOfBooks > 2) {
            preCost = dayCostAllBooksWithoutDiscount.multiply(daysRentNumber).multiply(discount);
        } else {
            preCost = dayCostAllBooksWithoutDiscount.multiply(daysRentNumber);
        }
        preCost.setScale(2, BigDecimal.ROUND_UP);

        return preCost;
    }

    private boolean checkFirstLastName(String name) {
        return name.matches(PATTERN_FIRST_NAME_LAST_NAME);
    }

    private boolean checkPatronymic(String patronymic) {
        return patronymic.matches(PATTERN_PATRONYMIC);
    }

    private boolean checkEmail(String email) {
        return email.matches(PATTERN_EMAIL);
    }

    private boolean checkPassportNumber(String passportNumber) {
        return passportNumber.matches(PATTERN_PASSPORT_NUMBER);
    }

    private boolean checkPostCode(int postcode) {
        String s = postcode + "";
        return s.matches(PATTERN_POST_CODE);
    }

    private boolean checkCountryLocalityStreet(String locality) {
        return locality.matches(PATTERN_COUNTRY_LOCALITY_STREET);
    }

    private boolean checkHouseNumber(int houseNumber) {
        String s = houseNumber + "";
        return s.matches(PATTERN_HOUSE_NUMBER);
    }

    private boolean checkHouseBuilding(String houseBuilding) {
        return houseBuilding.matches(PATTERN_BUILDING);
    }

    private boolean checkApartmentNumber(int apartmentNumber) {
        String s = apartmentNumber + "";
        return s.matches(PATTERN_APARTMENT_NUMBER);
    }

    private boolean checkDateOfBirth(LocalDate dateOfBirth) {
        LocalDate minDateOfBirth = LocalDate.of(1900, 1, 1);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return dateOfBirth.isAfter(minDateOfBirth) && dateOfBirth.isBefore(tomorrow);
    }

    private boolean checkTitle(String title) {
        return title.matches(PATTERN_TITLE);
    }

    private boolean checkOriginalTitle(String originalTitle) {
        return originalTitle.matches(PATTERN_ORIGINAL_TITLE);
    }

    private boolean checkCost(BigDecimal cost) {
        String c = cost + "";
        return c.matches(PATTERN_COST);
    }

    private boolean checkNumberOfCopies(int numberOfCopies) {
        if (numberOfCopies > 0) {
            return true;
        } else {
            throw new ServiceException("Invalid book data.");
        }
    }

    private boolean checkPublishingYear(int publishingYear) {
        if (publishingYear < 868 || publishingYear > LocalDate.now().getYear()) {
            return false;
        }
        return true;
    }

    private boolean checkNumberOfPages(int numberOfPages) {
        if (numberOfPages < 0 || numberOfPages > 2000) {
            return false;
        }
        return true;
    }

}
