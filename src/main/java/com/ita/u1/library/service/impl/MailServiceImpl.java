package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.MailDAO;

import com.ita.u1.library.entity.ViolationReturnDate;
import com.ita.u1.library.service.MailService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

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
            for (ViolationReturnDate violation : violations) {
                violation.setPenaltyAmountForDelaying(countPenaltyAmount(violation));
            }
        }
        return violations;
    }

    private BigDecimal countPenaltyAmount(ViolationReturnDate violation) {
        LocalDate today = LocalDate.now();
        BigDecimal numberOfOverdueDays = new BigDecimal(today.toEpochDay() - violation.getOrder().getPossibleReturnDate().toEpochDay());
        BigDecimal penaltyRate = new BigDecimal(PENALTY_RATE);
        BigDecimal amountOfThePenalty = violation.getOrder().getPreliminaryCost().multiply(numberOfOverdueDays).multiply(penaltyRate);
        amountOfThePenalty = amountOfThePenalty.setScale(2, RoundingMode.UP);
        return amountOfThePenalty;
    }
}
