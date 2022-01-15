package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.MailDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.entity.ViolationReturnDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

public class MailDAOImpl extends AbstractDAO implements MailDAO {

    private static final Logger log = LogManager.getLogger(MailDAOImpl.class);

    public MailDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<ViolationReturnDate> getViolationsReturnDeadlineToday() {

        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooksOrder = null;
        PreparedStatement psBooksTitle = null;
        PreparedStatement psClient = null;

        ResultSet rsOrder = null;
        ResultSet rsBooksOrder = null;
        ResultSet rsBooksTitle = null;
        ResultSet rsClient = null;

        List<ViolationReturnDate> violationsForToday = new ArrayList<>();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        try {
            psOrder = connection.prepareStatement(SELECT_ORDERS_OF_FRESH_RETURN_VIOLATIONS);
            psClient = connection.prepareStatement(SELECT_CLIENTS_EMAIL_BY_ID);
            psBooksOrder = connection.prepareStatement(SELECT_COPY_BOOK_OF_ORDER_BY_ID);
            psBooksTitle = connection.prepareStatement(SELECT_TITLES_COPY_BOOKS_ORDER);

            psOrder.setDate(1, Date.valueOf(yesterday));
            rsOrder = psOrder.executeQuery();

            if (rsOrder != null) {
                while (rsOrder.next()) {
                    ViolationReturnDate violationForToday = new ViolationReturnDate();
                    List<CopyBook> copyBooks = new ArrayList<>();
                    Client client = new Client();

                    int orderId = rsOrder.getInt(1);
                    int clientId = rsOrder.getInt(2);

                    psClient.setInt(1, clientId);
                    rsClient = psClient.executeQuery();
                    while (rsClient.next()) {
                        client.setEmail(rsClient.getString(1));
                    }

                    psBooksOrder.setInt(1, orderId);
                    rsBooksOrder = psBooksOrder.executeQuery();
                    while (rsBooksOrder.next()) {

                        psBooksTitle.setInt(1, rsBooksOrder.getInt(1));
                        rsBooksTitle = psBooksTitle.executeQuery();

                        while (rsBooksTitle.next()) {
                            copyBooks.add(new CopyBook(rsBooksTitle.getString(1)));
                        }
                    }
                    violationForToday.setClient(client);
                    violationForToday.setCopyBooks(copyBooks);
                    violationsForToday.add(violationForToday);
                }
            }
        } catch (SQLException e) {
            log.error("Method getViolationsReturnDeadlineToday()  failed.", e);
        } finally {
            close(rsOrder, rsBooksOrder, rsBooksTitle, rsClient);
            close(psOrder, psBooksOrder, psBooksTitle, psClient);
            release(connection);
        }

        return violationsForToday;
    }

    @Override
    public List<ViolationReturnDate> getViolationsReturnDeadlineFiveAndMoreDays() {

        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psClient = null;
        ResultSet rsOrder = null;
        ResultSet rsClient = null;

        List<ViolationReturnDate> violations = new ArrayList<>();
        LocalDate possibleReturnDate = LocalDate.now().minusDays(5);

        try {
            psOrder = connection.prepareStatement(SELECT_ORDERS_OF_OLD_RETURN_VIOLATIONS);
            psClient = connection.prepareStatement(SELECT_CLIENTS_EMAIL_BY_ID);

            psOrder.setDate(1, Date.valueOf(possibleReturnDate));
            rsOrder = psOrder.executeQuery();

            if (rsOrder != null) {
                while (rsOrder.next()) {
                    ViolationReturnDate violation = new ViolationReturnDate();
                    Client client = new Client();
                    Order order = new Order();

                    order.setPreliminaryCost(convertToBigDecimal(rsOrder.getString(2)));
                    order.setPossibleReturnDate(rsOrder.getDate(3).toLocalDate());

                    int clientId = rsOrder.getInt(1);
                    psClient.setInt(1, clientId);
                    rsClient = psClient.executeQuery();
                    while (rsClient.next()) {
                        client.setEmail(rsClient.getString(1));
                    }

                    violation.setClient(client);
                    violation.setOrder(order);
                    violations.add(violation);
                }
            }
        } catch (SQLException e) {
            log.error("Method getViolationsReturnDeadlineFiveAndMoreDays() failed.", e);
        } finally {
            close(rsOrder, rsClient);
            close(psOrder, psClient);
            release(connection);
        }
        return violations;
    }

    private BigDecimal convertToBigDecimal(String money) {
        String cost = money.replace(',', '.');
        BigDecimal bigDecimalCost = new BigDecimal(cost.replace(BR, EMPTY));
        return bigDecimalCost;
    }
}
