package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.DAOException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            psOrder = connection.prepareStatement("INSERT INTO orders (client_id, preliminary_cost, order_date, possible_return_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            psBooksOrder = connection.prepareStatement("INSERT INTO books_orders (order_id, copy_id) VALUES (?,?)");
            psBooksCopies = connection.prepareStatement("UPDATE books_copies SET availability = false WHERE id = ?");

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
    public boolean hasClientActiveOrder(Client client) {
        Connection connection = take();
        PreparedStatement psOrder = null;
        ResultSet rs = null;

        try {
            psOrder = connection.prepareStatement("SELECT * FROM orders WHERE client_id=? and status=true ");
            psOrder.setInt(1, client.getId());

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
            psOrder = connection.prepareStatement("SELECT * FROM orders WHERE client_id=? and status=true ");
            psBooksOrder = connection.prepareStatement("SELECT * FROM books_orders WHERE order_id=?");
            psBooks = connection.prepareStatement("SELECT title FROM books inner join books_copies on books.id=books_copies.book_id where books_copies.id=?");
            psBooksCopies = connection.prepareStatement("SELECT cost_per_day FROM books_copies where id=?");

            psOrder.setInt(1, clientId);
            rsOrder = psOrder.executeQuery();

            while (rsOrder.next()) {
                order.setId(rsOrder.getInt(1));
                String preliminaryCost = rsOrder.getString(3).replace(',', '.');
                order.setPreliminaryCost(new BigDecimal(preliminaryCost.replace(" Br", "")));
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
                    String cost = rsBooksCopies.getString(1).replace(',', '.');
                    copy.setCostPerDay(new BigDecimal(cost.replace(" Br", "")));
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
            psBooksOrder = connection.prepareStatement("UPDATE books_orders SET violation=? WHERE order_id=? and copy_id=?");
            psViolationImage = connection.prepareStatement("INSERT INTO violation_images (order_id, copy_id, image) VALUES (?,?,?)");

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
            psOrder = connection.prepareStatement("UPDATE orders SET total_cost=?, real_return_date=?, status=false, penalty=? WHERE id=?");
            psBooksOrder = connection.prepareStatement("UPDATE books_orders SET rating=? where order_id=? and copy_id=?");
            psBooksCopies=connection.prepareStatement("UPDATE books_copies SET availability=true where id=?");

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
                psBooksCopies.setInt(1,order.getBooks().get(i).getId());
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
            psCopyBook = connection.prepareStatement("SELECT * FROM books_copies where id=?");
            for (int i = 0; i < order.getBooks().size(); i++) {
                psCopyBook.setInt(1, order.getBooks().get(i).getId());
                rsBooksCopies = psCopyBook.executeQuery();
                while (rsBooksCopies.next()) {
                    CopyBook copy = new CopyBook();
                    copy.setId(rsBooksCopies.getInt(1));
                    copy.setBookId(rsBooksCopies.getInt(2));
                    String cost = rsBooksCopies.getString(3).replace(',', '.');
                    copy.setCostPerDay(new BigDecimal(cost.replace(" Br", "")));
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
}
