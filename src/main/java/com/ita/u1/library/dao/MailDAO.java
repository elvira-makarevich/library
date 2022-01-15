package com.ita.u1.library.dao;

import com.ita.u1.library.entity.ViolationReturnDate;

import java.util.List;

public interface MailDAO {

    List<ViolationReturnDate> getViolationsReturnDeadlineToday();
    List<ViolationReturnDate> getViolationsReturnDeadlineFiveAndMoreDays();
}
