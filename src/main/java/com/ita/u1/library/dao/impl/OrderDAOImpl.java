package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.OrderDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.entity.Order;
import com.ita.u1.library.exception.DAOException;

import java.sql.*;

public class OrderDAOImpl extends AbstractDAO implements OrderDAO {

    public OrderDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void saveOrder(Order order) {

        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooksOrder = null;
        ResultSet generatedKeys = null;

        try {
            connection.setAutoCommit(false);
            psOrder = connection.prepareStatement("INSERT INTO orders (client_id, preliminary_cost, order_date, possible_return_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            psBooksOrder = connection.prepareStatement("INSERT INTO books_orders (order_id, copy_id) VALUES (?,?)");

            System.out.println(order.getOrderDate());
            System.out.println(order.getPossibleReturnDate());
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
            close(psOrder, psBooksOrder);
            release(connection);
        }
    }

    @Override
    public boolean hasClientActiveOrder(Client client) {
        Connection connection = take();
        PreparedStatement psOrder = null;
        ResultSet rs = null;

        try {
            psOrder = connection.prepareStatement("SELECT * FROM orders WHERE client_id=? ");
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
}
