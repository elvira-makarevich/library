package com.ita.u1.library.service.validator;

import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static com.ita.u1.library.util.ConstantParameter.*;

public class ServiceValidator {

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

    public void validateTitle(String title){
        boolean result = checkTitle(title);
        if (!result) {
            throw new ServiceException("Invalid title.");
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

        BigDecimal preCost = calculatePreliminaryCost(order, copyBooks, numberOfBooks);
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        compareCost(preCost, order.getPreliminaryCost());

    }

    public void validateCloseOrder(Order order, Order orderInfoFromDB) {
        checkDates(order, orderInfoFromDB);
        checkPenaltyNullOrNotNegative(order.getPenalty());

        BigDecimal totalCostValueDB = calculateTotalCostBasedOnDataFromDB(order, orderInfoFromDB);

        BigDecimal totalCostValueWithoutPenalty;
        if (order.getPenalty() == null) {
            totalCostValueWithoutPenalty = order.getTotalCost();
        } else {
            totalCostValueWithoutPenalty = order.getTotalCost().subtract(order.getPenalty());
        }
        compareCost(totalCostValueDB, totalCostValueWithoutPenalty);
    }

    public void validateViolationMessage(Violation violation) {
        if (violation.getMessage().matches(PATTERN_VIOLATION_MESSAGE)) {
            throw new ServiceException("Invalid message.");
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

    private BigDecimal calculatePreliminaryCost(Order order, List<CopyBook> copyBooks, int numberOfBooks) {

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

    private void compareCost(BigDecimal cost1, BigDecimal cost2) {
        //возможно поменяется на compareTo BigDecimal
        double d1 = cost1.doubleValue();
        double d2 = cost2.doubleValue();

        double difference = Math.abs(d1 - d2);
        System.out.println(difference);

        if (difference > 0.3) {
            throw new ServiceException("Invalid order cost.");
        }
    }

    private void checkDates(Order order, Order orderInfoFromDB) {
        if (order.getOrderDate().compareTo(orderInfoFromDB.getOrderDate()) != 0) {
            throw new ServiceException("Invalid data while closing order.");
        }

        if (order.getPossibleReturnDate().compareTo(orderInfoFromDB.getPossibleReturnDate()) != 0) {
            throw new ServiceException("Invalid data while closing order.");
        }

    }

    private BigDecimal calculateTotalCostBasedOnDataFromDB(Order order, Order orderInfoFromDB) {
        BigDecimal realNumberOfRentalDays = new BigDecimal(order.getRealReturnDate().toEpochDay() - order.getOrderDate().toEpochDay() + 1);
        BigDecimal numberOfPossibleRentalDays = new BigDecimal(order.getPossibleReturnDate().toEpochDay() - order.getOrderDate().toEpochDay() + 1);
        BigDecimal totalCostValue;

        if (isReturnDateViolated(order)) {
            BigDecimal numberOfOverdueDays = realNumberOfRentalDays.subtract(numberOfPossibleRentalDays);
            BigDecimal penaltyRate = new BigDecimal(0.01);
            BigDecimal amountOfThePenalty = orderInfoFromDB.getPreliminaryCost().multiply(numberOfOverdueDays).multiply(penaltyRate);
            totalCostValue = orderInfoFromDB.getPreliminaryCost().add(amountOfThePenalty);
        } else {
            BigDecimal rentalCostPerDay = orderInfoFromDB.getPreliminaryCost().divide(numberOfPossibleRentalDays);
            totalCostValue = rentalCostPerDay.multiply(realNumberOfRentalDays);
        }
        return totalCostValue.setScale(2, BigDecimal.ROUND_UP);
    }

    private boolean isReturnDateViolated(Order order) {

        if (order.getPossibleReturnDate().compareTo(order.getRealReturnDate()) < 0) {
            return true;
        }
        return false;
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
        if (originalTitle == null) {
            return true;
        }
        return originalTitle.matches(PATTERN_ORIGINAL_TITLE);
    }

    private boolean checkCost(BigDecimal cost) {
        String c = cost + "";
        return c.matches(PATTERN_COST);
    }

    private boolean checkPenaltyNullOrNotNegative(BigDecimal penalty) {
        if (penalty == null) {
            return true;
        }
        String c = penalty + "";
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
        if (publishingYear==0){
            return true;
        }
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
