package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public Order findOrderInfo(Client client) {
        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooksOrder = null;
        PreparedStatement psBooks = null;
        ResultSet rsOrder = null;
        ResultSet rsBooksOrder = null;
        ResultSet rsBooks = null;

        Order order = new Order();
        List<CopyBook> booksOrder = new ArrayList<>();

        try {
            psOrder = connection.prepareStatement("SELECT * FROM orders WHERE client_id=? and status=true ");
            psBooksOrder = connection.prepareStatement("SELECT * FROM books_orders WHERE order_id=?");
            psBooks = connection.prepareStatement("SELECT title FROM books inner join books_copies on books.id=books_copies.book_id where books_copies.id=?");

            psOrder.setInt(1, client.getId());
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
                psBooks.setInt(1,copy.getId());
                rsBooks=psBooks.executeQuery();
                while (rsBooks.next()){
                    copy.setTitle(rsBooks.getString(1));
                }
                booksOrder.add(copy);
            }

            order.setBooks(booksOrder);

        } catch (SQLException e) {
            throw new DAOException("Method findOrderInfo() failed.", e);
        } finally {

            release(connection);
        }

        return order;
    }
}
