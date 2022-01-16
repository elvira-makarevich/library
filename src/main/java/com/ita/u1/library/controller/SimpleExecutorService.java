package com.ita.u1.library.controller;

import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.ViolationReturnDate;
import com.ita.u1.library.service.MailService;
import com.ita.u1.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.ita.u1.library.util.ConstantParameter.*;

public class SimpleExecutorService {

    private ScheduledExecutorService executor;

    public SimpleExecutorService() {
        LocalDate today = LocalDate.now();
        LocalDateTime timeToSendMail = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 11, 17);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, timeToSendMail);
        long delay = Math.abs(duration.toMillis());

        executor = Executors.newScheduledThreadPool(1);
        Thread mailSender = new Thread(new MailSender());
        mailSender.setDaemon(true);
        executor.schedule(mailSender, delay, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }


    private static class MailSender implements Runnable {

        private static final MailService emailService = ServiceProvider.getInstance().getMailService();
        private static final Logger log = LogManager.getLogger(MailSender.class);

        @Override
        public void run() {

            List<ViolationReturnDate> freshViolationReturnDates = emailService.getViolationsReturnDeadlineToday();
            List<ViolationReturnDate> oldViolationReturnDates = emailService.getViolationsReturnDeadlineFiveAndMoreDays();

            if (!freshViolationReturnDates.isEmpty() || !oldViolationReturnDates.isEmpty()) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle(MAIL_RESOURCE);
                Properties properties = new Properties();
                properties.put(MAIL_HOST, resourceBundle.getString(MAIL_HOST));
                properties.put(MAIL_PORT, resourceBundle.getString(MAIL_PORT));
                properties.put(MAIL_FROM, resourceBundle.getString(MAIL_FROM));
                properties.put(MAIL_PASSWORD, resourceBundle.getString(MAIL_PASSWORD));
                properties.put(MAIL_AUTH, resourceBundle.getString(MAIL_AUTH));
                properties.put(MAIL_STARTTLS_ENABLE, resourceBundle.getString(MAIL_STARTTLS_ENABLE));

                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(resourceBundle.getString(MAIL_FROM), resourceBundle.getString(MAIL_PASSWORD));
                    }
                });

                if (!freshViolationReturnDates.isEmpty()) {
                    for (int i = 0; i < freshViolationReturnDates.size(); i++) {
                        try {
                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(resourceBundle.getString(MAIL_FROM)));
                            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(freshViolationReturnDates.get(i).getClient().getEmail()));
                            message.setSubject(MAIL_MESSAGE_SUBJECT);
                            message.setText(formMessageTextWithCopyBooks(freshViolationReturnDates.get(i).getCopyBooks()));
                            Transport.send(message);
                            log.info("Email is successfully sent to " + freshViolationReturnDates.get(i).getClient().getEmail());
                        } catch (MessagingException e) {
                            log.error("Exception while sending mail.", e);
                        }
                    }
                }

                if (!oldViolationReturnDates.isEmpty()) {
                    for (int i = 0; i < oldViolationReturnDates.size(); i++) {
                        try {
                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(resourceBundle.getString(MAIL_FROM)));
                            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(oldViolationReturnDates.get(i).getClient().getEmail()));
                            message.setSubject(MAIL_MESSAGE_SUBJECT);
                            message.setText(formMessageTextWithAmountOfPenalty(oldViolationReturnDates.get(i).getPenaltyAmountForDelaying()));
                            Transport.send(message);
                            log.info("Email is successfully sent to " + oldViolationReturnDates.get(i).getClient().getEmail());
                        } catch (MessagingException e) {
                            log.error("Exception while sending mail.", e);
                        }
                    }
                }
            }
        }

        private static String formMessageTextWithCopyBooks(List<CopyBook> copyBooks) {

            StringBuilder sb = new StringBuilder(BOOKS_TO_RETURN);
            for (CopyBook copy : copyBooks) {
                sb.append(copy.getTitle()).append(HYPHENATION);
            }
            return sb.toString();
        }

        private static String formMessageTextWithAmountOfPenalty(BigDecimal amountOfPenalty) {

            StringBuilder sb = new StringBuilder(AMOUNT_OF_PENALTY_FOR_DELAYING);
            sb.append(amountOfPenalty).append(BR);
            return sb.toString();
        }
    }
}
