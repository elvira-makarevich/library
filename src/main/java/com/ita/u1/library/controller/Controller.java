package com.ita.u1.library.controller;

import com.ita.u1.library.entity.Client;
import com.ita.u1.library.service.EmailService;
import com.ita.u1.library.service.ServiceProvider;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@MultipartConfig
@WebServlet(asyncSupported = true)
public class Controller extends HttpServlet {

    private static final String REQUEST_PARAM_COMMAND = "command";

    private static final String SENDER_EMAIL_ADDRESS = "library14012022@gmail.com";
    private static final String SENDER_EMAIL_PASSWORD = "library1401";
    private static final String SENDER_HOST = "smtp.gmail.com";
    private static final String SENDER_PORT = "587";

    private final CommandProvider provider = new CommandProvider();

    @Resource
    private ExecutorService executor;

    @Override
    public void init() {
        executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                final EmailService emailService = ServiceProvider.getInstance().getEmailService();
                List<Client> clients = emailService.getClientsWithViolatedReturnDeadlineToday();
                System.out.println(clients.get(0).getEmail());


                Properties properties = new Properties();
                properties.put("mail.smtp.host", SENDER_HOST);
                properties.put("mail.smtp.port", SENDER_PORT);
                properties.put("mail.from", SENDER_EMAIL_ADDRESS);
                properties.put("mail.smtp.password", SENDER_EMAIL_PASSWORD);
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");


                Session session = Session.getInstance(properties,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD);
                            }
                        });

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(clients.get(0).getEmail()));
                    message.setSubject("Violation of the deadline for the return of books");
                    message.setText("Violation of the deadline for the return of books");
                    Transport.send(message);
                    System.out.println("Email is successfully sent!");

                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);

    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String commandName = request.getParameter(REQUEST_PARAM_COMMAND);
        Command command = provider.findCommand(commandName);
        command.execute(request, response);
    }

}
