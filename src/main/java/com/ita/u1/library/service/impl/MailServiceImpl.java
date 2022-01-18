package com.ita.u1.library.service.impl;

import com.ita.u1.library.dao.MailDAO;

import com.ita.u1.library.entity.ViolationReturnDate;
import com.ita.u1.library.service.MailService;

import java.math.BigDecimal;
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
        if (freshViolations.isEmpty() || freshViolations == null) {
            return Collections.emptyList();
        }
        return freshViolations;
    }

    @Override
    public List<ViolationReturnDate> getViolationsReturnDeadlineFiveAndMoreDays() {

        List<ViolationReturnDate> violations = mailDAO.getViolationsReturnDeadlineFiveAndMoreDays();
        if (violations.isEmpty() || violations == null) {
            return Collections.emptyList();
        } else {
            LocalDate today = LocalDate.now();
            for (int i = 0; i < violations.size(); i++) {
                BigDecimal numberOfOverdueDays = new BigDecimal(today.toEpochDay() - violations.get(i).getOrder().getPossibleReturnDate().toEpochDay());
                BigDecimal penaltyRate = new BigDecimal(0.01);
                BigDecimal amountOfThePenalty = violations.get(i).getOrder().getPreliminaryCost().multiply(numberOfOverdueDays).multiply(penaltyRate);
                violations.get(i).setPenaltyAmountForDelaying(amountOfThePenalty.setScale(2, BigDecimal.ROUND_UP));
            }
        }
        return violations;
    }
}
