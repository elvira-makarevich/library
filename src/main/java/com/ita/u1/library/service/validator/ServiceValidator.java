package com.ita.u1.library.service.validator;

import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

public class ServiceValidator {

    private static final Logger log = LogManager.getLogger(ServiceValidator.class);

    public void validateBookRegistrationInfo(Book book) {
        log.info("Start validate book registration info.");
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
        log.info("Book registration info validated.");
    }

    public void validateAuthorRegistrationInfo(Author author) {
        log.info("Start validate author registration info.");
        boolean result = checkFirstLastName(author.getFirstName()) &&
                checkFirstLastName(author.getLastName());
        if (!result) {
            throw new ServiceException("Invalid author data.");
        }
        log.info("Author registration info validated.");
    }

    public void validateClientRegistrationInfo(Client client) {
        log.info("Start validate client registration info.");
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
        log.info("Client registration info validated.");
    }

    public void validatePassportNumber(String passportNumber) {
        log.info("Start validate passport number.");
        boolean result = checkPassportNumber(passportNumber);
        if (!result) {
            throw new ServiceException("Invalid passport number.");
        }
        log.info("Passport number validated.");
    }

    public void validateEmail(String email) {
        log.info("Start validate passport email.");
        boolean result = checkEmail(email);
        if (!result) {
            throw new ServiceException("Invalid email.");
        }
        log.info("Email validated.");
    }

    public void validateLastName(String lastName) {
        log.info("Start validate last name.");
        boolean result = checkFirstLastName(lastName);
        if (!result) {
            throw new ServiceException("Invalid last name.");
        }
        log.info("Last name validated.");
    }

    public void validateTitle(String title) {
        log.info("Start validate title.");
        boolean result = checkTitle(title);
        if (!result) {
            throw new ServiceException("Invalid title.");
        }
        log.info("Title validated.");
    }

    public void validateCost(BigDecimal cost) {
        log.info("Start validate cost.");
        boolean result = checkCost(cost);
        if (!result) {
            throw new ServiceException("Invalid cost.");
        }
        log.info("Cost validated.");
    }

    public void validateSaveOrder(Order order, List<CopyBook> copyBooks) {
        log.info("Start validate order info.");
        checkTheDuplicationOfBooks(order.getBooks());

        int numberOfBooks = order.getBooks().size();
        if (numberOfBooks > 5) {
            throw new ServiceException("Invalid order. The maximum number of books in an order is 5.");
        }

        BigDecimal preCost = calculatePreliminaryCost(order, copyBooks, numberOfBooks);
        compareCost(preCost, order.getPreliminaryCost());
        log.info("Order info validated.");
    }

    public void validateCloseOrder(Order order, Order orderInfoFromDB) {
        log.info("Start validate close order info.");
        checkDates(order, orderInfoFromDB);
        checkPenaltyNullOrNotNegative(order.getPenalty());

        BigDecimal totalCostValueDB = calculateTotalCostBasedOnDataFromDB(orderInfoFromDB);
        BigDecimal totalCostValueWithoutPenalty;
        if (order.getPenalty() == null) {
            totalCostValueWithoutPenalty = order.getTotalCost();
        } else {
            totalCostValueWithoutPenalty = order.getTotalCost().subtract(order.getPenalty());
        }
        compareCost(totalCostValueDB, totalCostValueWithoutPenalty);
        log.info("Order closing info validated.");
    }

    public void validateViolationMessage(Violation violation) {
        log.info("Start validate violation message.");
        if (!violation.getMessage().matches(PATTERN_VIOLATION_MESSAGE)) {
            throw new ServiceException("Invalid message.");
        }
        log.info("Violation message validated.");
    }

    public void validateProfitabilityDates(Profitability profitabilityDates) {
        if (profitabilityDates.getFrom().isAfter(profitabilityDates.getTo())) {
            throw new ServiceException("Invalid dates to check profitability.");
        }
    }

    public void validateTheDuplicationOfCopyBooks(List<CopyBook> copyBooks) {

        for (int i = 0; i < copyBooks.size(); i++) {
            int bookId = copyBooks.get(i).getBookId();
            for (int j = 0; j < copyBooks.size(); j++) {
                if (i == j) continue;
                if (bookId == copyBooks.get(j).getBookId()) {
                    throw new ServiceException("The list contains copy books with the same id.");
                }
            }
        }
    }

    private void checkTheDuplicationOfBooks(List<CopyBook> copyBooks) {
        log.info("List<CopyBook> copyBooks");
        for (int i = 0; i < copyBooks.size(); i++) {
            String title = copyBooks.get(i).getTitle();
            for (int j = 0; j < copyBooks.size(); j++) {
                if (i == j) continue;
                if (title.equals(copyBooks.get(j).getTitle())) {
                    System.out.println(title);
                    throw new ServiceException("The order contains the same books.");
                }
            }
        }
        log.info("List<CopyBook> copyBooks");
    }

    private BigDecimal calculatePreliminaryCost(Order order, List<CopyBook> copyBooks, int numberOfBooks) {

        BigDecimal daysRentNumber = new BigDecimal(order.getPossibleReturnDate().toEpochDay() - order.getOrderDate().toEpochDay() + 1);
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
        log.info("Start compare cost with the info from DB.");
        if (cost1.compareTo(cost2) != 0) {
            throw new ServiceException("Invalid order cost.");
        }
        log.info("Cost compared.");
    }

    private void checkDates(Order order, Order orderInfoFromDB) {
        if (!order.getOrderDate().isEqual(orderInfoFromDB.getOrderDate())) {
            throw new ServiceException("Invalid data while closing order.");
        }

        if (!order.getPossibleReturnDate().isEqual(orderInfoFromDB.getPossibleReturnDate())) {
            throw new ServiceException("Invalid data while closing order.");
        }

    }

    public BigDecimal calculateTotalCostBasedOnDataFromDB(Order orderInfoFromDB) {
        BigDecimal realNumberOfRentalDays = new BigDecimal(LocalDate.now().toEpochDay() - orderInfoFromDB.getOrderDate().toEpochDay() + 1);
        BigDecimal numberOfPossibleRentalDays = new BigDecimal(orderInfoFromDB.getPossibleReturnDate().toEpochDay() - orderInfoFromDB.getOrderDate().toEpochDay() + 1);
        BigDecimal totalCostValue;

        if (isReturnDateViolated(orderInfoFromDB)) {
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

        if (order.getPossibleReturnDate().isBefore(LocalDate.now())) {
            return true;
        }
        return false;
    }

    private boolean checkFirstLastName(String name) {
        return name.matches(PATTERN_FIRST_NAME_LAST_NAME);
    }

    private boolean checkPatronymic(String patronymic) {
        if (patronymic == null) {
            return true;
        }
        return patronymic.matches(PATTERN_PATRONYMIC);
    }

    private boolean checkEmail(String email) {
        return email.matches(PATTERN_EMAIL);
    }

    private boolean checkPassportNumber(String passportNumber) {
        if (passportNumber == null) {
            return true;
        }
        return passportNumber.matches(PATTERN_PASSPORT_NUMBER);
    }

    private boolean checkPostCode(int postcode) {
        String s = postcode + EMPTY;
        return s.matches(PATTERN_POST_CODE);
    }

    private boolean checkCountryLocalityStreet(String locality) {
        return locality.matches(PATTERN_COUNTRY_LOCALITY_STREET);
    }

    private boolean checkHouseNumber(int houseNumber) {
        String s = houseNumber + EMPTY;
        return s.matches(PATTERN_HOUSE_NUMBER);
    }

    private boolean checkHouseBuilding(String houseBuilding) {
        if (houseBuilding == null) {
            return true;
        }
        return houseBuilding.matches(PATTERN_BUILDING);
    }

    private boolean checkApartmentNumber(int apartmentNumber) {
        String s = apartmentNumber + EMPTY;
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
        String c = cost + EMPTY;
        return c.matches(PATTERN_COST);
    }

    private boolean checkPenaltyNullOrNotNegative(BigDecimal penalty) {
        if (penalty == null) {
            return true;
        }
        String c = penalty + EMPTY;
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
        if (publishingYear == 0) {
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
