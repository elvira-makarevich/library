package com.ita.u1.library.service;


import com.ita.u1.library.entity.ViolationReturnDate;

import java.util.List;

public interface MailService {

    List<ViolationReturnDate> getViolationsReturnDeadlineToday();

    List<ViolationReturnDate> getViolationsReturnDeadlineFiveAndMoreDays();
}
