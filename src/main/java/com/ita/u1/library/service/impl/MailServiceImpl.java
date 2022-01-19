package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.MailDAO;

import com.ita.u1.library.entity.ViolationReturnDate;
import com.ita.u1.library.service.MailService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class MailServiceImpl implements MailService {

    private final MailDAO mailDAO;

    public MailServiceImpl(MailDAO mailDAO) {
        this.mailDAO = mailDAO;
    }

    @Override
    public List<ViolationReturnDate> getViolationsReturnDeadlineToday() {
        List<ViolationReturnDate> freshViolations = mailDAO.getViolationsReturnDeadlineToday();
        if (freshViolations.isEmpty()) {
            return Collections.emptyList();
        }
        return freshViolations;
    }

    @Override
    public List<ViolationReturnDate> getViolationsReturnDeadlineFiveAndMoreDays() {

        List<ViolationReturnDate> violations = mailDAO.getViolationsReturnDeadlineFiveAndMoreDays();
        if (violations.isEmpty()) {
            return Collections.emptyList();
        } else {
            LocalDate today = LocalDate.now();
            for (ViolationReturnDate violation : violations) {
                BigDecimal numberOfOverdueDays = new BigDecimal(today.toEpochDay() - violation.getOrder().getPossibleReturnDate().toEpochDay());
                BigDecimal penaltyRate = new BigDecimal("0.01");
                BigDecimal amountOfThePenalty = violation.getOrder().getPreliminaryCost().multiply(numberOfOverdueDays).multiply(penaltyRate);
                violation.setPenaltyAmountForDelaying(amountOfThePenalty.setScale(2, RoundingMode.UP));
            }
        }
        return violations;
    }
}
