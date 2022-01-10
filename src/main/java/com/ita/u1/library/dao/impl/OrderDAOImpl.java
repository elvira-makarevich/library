package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ita.u1.library.util.ConstantParameter.*;

public class OrderDAOImpl extends AbstractDAO implements OrderDAO {

    public OrderDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void saveOrder(Order order) {

        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooksOrder = null;
        PreparedStatement psBooksCopies = null;
        ResultSet generatedKeys = null;

        try {
            connection.setAutoCommit(false);
            psOrder = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            psBooksOrder = connection.prepareStatement(INSERT_BOOKS_ORDER);
            psBooksCopies = connection.prepareStatement(UPDATE_BOOKS_COPIES_AVAILABILITY_FALSE);

            psOrder.setInt(1, order.getClientId());
            psOrder.setBigDecimal(2, order.getPreliminaryCost());
            psOrder.setDate(3, Date.valueOf(order.getOrderDate()));
            psOrder.setDate(4, Date.valueOf(order.getPossibleReturnDate()));

            int affectedRows = psOrder.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException("Saving order failed, no rows affected.");
            }
            generatedKeys = psOrder.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("Saving order failed, no ID obtained.");
            }


            for (int i = 0; i < order.getBooks().size(); i++) {
                psBooksOrder.setInt(1, order.getId());
                psBooksOrder.setInt(2, order.getBooks().get(i).getId());
                psBooksOrder.executeUpdate();
            }

            for (int i = 0; i < order.getBooks().size(); i++) {
                psBooksCopies.setInt(1, order.getBooks().get(i).getId());
                psBooksCopies.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: save order.", ex);
                }
            throw new DAOException("Saving order to database failed.", e);
        } finally {
            close(generatedKeys);
            close(psOrder, psBooksOrder, psBooksCopies);
            release(connection);
        }
    }

    @Override
    public boolean hasClientActiveOrder(int clientId) {
        Connection connection = take();
        PreparedStatement psOrder = null;
        ResultSet rs = null;

        try {
            psOrder = connection.prepareStatement(SELECT_ACTIVE_CLIENT_ORDER);
            psOrder.setInt(1, clientId);

            rs = psOrder.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method hasClientActiveOrder() failed.", e);
        } finally {
            close(rs);
            close(psOrder);
            release(connection);
        }
        return false;
    }

    @Override
    public Optional<Order> findOrderInfo(int clientId) {
        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooksOrder = null;
        PreparedStatement psBooks = null;
        PreparedStatement psBooksCopies = null;
        ResultSet rsOrder = null;
        ResultSet rsBooksOrder = null;
        ResultSet rsBooks = null;
        ResultSet rsBooksCopies = null;

        Optional<Order> optionalOrder;
        Order order = new Order();
        List<CopyBook> booksOrder = new ArrayList<>();

        try {
            psOrder = connection.prepareStatement(SELECT_ACTIVE_CLIENT_ORDER);
            psBooksOrder = connection.prepareStatement(SELECT_BOOKS_ORDER_BY_ID);
            psBooks = connection.prepareStatement(SELECT_BOOKS_TITLE);
            psBooksCopies = connection.prepareStatement(SELECT_BOOK_COPY_COST_PER_DAY);

            psOrder.setInt(1, clientId);
            rsOrder = psOrder.executeQuery();

            while (rsOrder.next()) {
                order.setId(rsOrder.getInt(1));
                order.setPreliminaryCost(convertToBigDecimal(rsOrder.getString(3)));
                order.setOrderDate(rsOrder.getDate(5).toLocalDate());
                order.setPossibleReturnDate(rsOrder.getDate(6).toLocalDate());
            }

            psBooksOrder.setInt(1, order.getId());
            rsBooksOrder = psBooksOrder.executeQuery();

            while (rsBooksOrder.next()) {
                CopyBook copy = new CopyBook();
                copy.setId(rsBooksOrder.getInt(3));

                psBooks.setInt(1, copy.getId());
                rsBooks = psBooks.executeQuery();
                while (rsBooks.next()) {
                    copy.setTitle(rsBooks.getString(1));

                }

                psBooksCopies.setInt(1, copy.getId());
                rsBooksCopies = psBooksCopies.executeQuery();
                while (rsBooksCopies.next()) {
                    copy.setCostPerDay(convertToBigDecimal(rsBooksCopies.getString(1)));
                }

                booksOrder.add(copy);
            }

            order.setBooks(booksOrder);

        } catch (SQLException e) {
            throw new DAOException("Method findOrderInfo() failed.", e);
        } finally {
            close(psOrder, psBooksOrder, psBooksCopies, psBooks);
            close(rsOrder, rsBooks, rsBooksCopies, rsBooksOrder);
            release(connection);
        }

        optionalOrder = Optional.of(order);
        return optionalOrder;
    }

    @Override
    public void indicateBookViolation(Violation violation) {

        Connection connection = take();
        PreparedStatement psBooksOrder = null;
        PreparedStatement psViolationImage = null;

        try {
            connection.setAutoCommit(false);
            psBooksOrder = connection.prepareStatement(UPDATE_BOOKS_ORDER_WITH_VIOLATION);
            psViolationImage = connection.prepareStatement(INSERT_VIOLATION_IMAGES);

            psBooksOrder.setString(1, violation.getMessage());
            psBooksOrder.setInt(2, violation.getOrderId());
            psBooksOrder.setInt(3, violation.getCopyId());
            psBooksOrder.executeUpdate();

            for (int i = 0; i < violation.getImages().size(); i++) {
                psViolationImage.setInt(1, violation.getOrderId());
                psViolationImage.setInt(2, violation.getCopyId());
                psViolationImage.setBytes(3, violation.getImages().get(i));
                psViolationImage.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: indicateBookViolationAndChangeCost().", ex);
                }
            throw new DAOException("Method indicateBookViolationAndChangeCost() failed.", e);
        } finally {
            close(psBooksOrder, psViolationImage);
            release(connection);
        }
    }

    @Override
    public void closeOrder(Order order) {

        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooksOrder = null;
        PreparedStatement psBooksCopies = null;

        try {
            connection.setAutoCommit(false);
            psOrder = connection.prepareStatement(UPDATE_ORDER_CLOSE);
            psBooksOrder = connection.prepareStatement(UPDATE_BOOKS_ORDER_WITH_RATING);
            psBooksCopies = connection.prepareStatement(UPDATE_BOOKS_COPIES_AVAILABILITY_TRUE);

            psOrder.setBigDecimal(1, order.getTotalCost());
            psOrder.setDate(2, Date.valueOf(order.getRealReturnDate()));
            psOrder.setBigDecimal(3, order.getPenalty());
            psOrder.setInt(4, order.getId());
            psOrder.executeUpdate();

            for (int i = 0; i < order.getBooks().size(); i++) {
                psBooksOrder.setDouble(1, order.getBooks().get(i).getRating());
                psBooksOrder.setInt(2, order.getId());
                psBooksOrder.setInt(3, order.getBooks().get(i).getId());
                psBooksOrder.executeUpdate();
            }

            for (int i = 0; i < order.getBooks().size(); i++) {
                psBooksCopies.setInt(1, order.getBooks().get(i).getId());
                psBooksCopies.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: closeOrder().", ex);
                }
            throw new DAOException("Method closeOrder() failed.", e);
        } finally {
            close(psBooksOrder, psOrder, psBooksCopies);
            release(connection);
        }
    }

    @Override
    public List<CopyBook> findCopyBookInfo(Order order) {

        List<CopyBook> books = new ArrayList<>();
        Connection connection = take();
        PreparedStatement psCopyBook = null;
        ResultSet rsBooksCopies = null;

        try {
            psCopyBook = connection.prepareStatement(SELECT_BOOKS_COPIES_ORDER);
            for (int i = 0; i < order.getBooks().size(); i++) {
                psCopyBook.setInt(1, order.getBooks().get(i).getId());
                rsBooksCopies = psCopyBook.executeQuery();
                while (rsBooksCopies.next()) {
                    CopyBook copy = new CopyBook();
                    copy.setId(rsBooksCopies.getInt(1));
                    copy.setBookId(rsBooksCopies.getInt(2));
                    copy.setCostPerDay(convertToBigDecimal(rsBooksCopies.getString(3)));
                    copy.setAvailability(rsBooksCopies.getBoolean(4));
                    books.add(copy);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Method findCopyBookInfo() failed.", e);
        } finally {
            close(rsBooksCopies);
            close(psCopyBook);
            release(connection);
        }
        return books;
    }

    @Override
    public boolean doesTheOrderExist(int orderId, int copyId) {
        Connection connection = take();
        PreparedStatement psOrder = null;
        ResultSet rs = null;

        try {
            psOrder = connection.prepareStatement(SELECT_COPY_BOOK_FROM_ORDER);
            psOrder.setInt(1, orderId);
            psOrder.setInt(2, copyId);

            rs = psOrder.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method doesTheOrderExist() failed.", e);
        } finally {
            close(rs);
            close(psOrder);
            release(connection);
        }
        return false;
    }

    private BigDecimal convertToBigDecimal(String money) {
        String cost = money.replace(',', '.');
        BigDecimal bigDecimalCost = new BigDecimal(cost.replace(" Br", ""));
        return bigDecimalCost;
    }

}
