package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.DAOException;

import java.sql.*;

public class ClientDAOImpl extends AbstractDAO implements ClientDAO {

    public ClientDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void add(Client client) {

        Connection connection = take();
        try (PreparedStatement psClient = connection.prepareStatement("INSERT INTO clients (first_name, last_name, patronymic, passport_number, email, birthday, postcode, country, locality, street, house_number, building, apartment_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psClientImage = connection.prepareStatement("INSERT INTO clients_images (client_id, image) VALUES (?,?)")) {

            connection.setAutoCommit(false);

            psClient.setString(1, client.getFirstName());
            psClient.setString(2, client.getLastName());
            psClient.setString(3, client.getPatronymic());
            psClient.setString(4, client.getPassportNumber());
            psClient.setString(5, client.getEmail());
            psClient.setDate(6, new java.sql.Date(client.getDateOfBirth().getTime()));
            psClient.setInt(7, client.getAddress().getPostcode());
            psClient.setString(8, client.getAddress().getCountry());
            psClient.setString(9, client.getAddress().getLocality());
            psClient.setString(10, client.getAddress().getStreet());
            psClient.setInt(11, client.getAddress().getHouseNumber());
            psClient.setString(12, client.getAddress().getBuilding());
            psClient.setInt(13, client.getAddress().getApartmentNumber());

            int affectedRows = psClient.executeUpdate();

            if (affectedRows == 0) {

                throw new DAOException("Adding new client failed, no rows affected.");
            }
            try (ResultSet generatedKeys = psClient.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Adding new client failed, no ID obtained.");
                }
            }

            psClientImage.setInt(1, client.getId());
            psClientImage.setBytes(2, client.getImage());
            psClientImage.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: add client.", ex);
                }
            throw new DAOException("Adding book to database failed.", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new DAOException("Adding client to database failed.", ex);
                }
            }
            release(connection);
        }

    }

    @Override
    public boolean checkUniquenessPassportNumber(String passportNumber) {

        Connection connection = take();

        try (PreparedStatement psClient = connection.prepareStatement("SELECT * FROM clients WHERE passport_number=? ")) {

            psClient.setString(1, passportNumber);

            ResultSet rs = psClient.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method checkUniquenessPassportNumber() failed.", e);
        } finally {
            release(connection);
        }

        return false;
    }

    @Override
    public boolean checkUniquenessEmail(String email) {
        Connection connection = take();

        try (PreparedStatement psClient = connection.prepareStatement("SELECT * FROM clients WHERE email=? ")) {

            psClient.setString(1, email);

            ResultSet rs = psClient.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method checkUniquenessEmail() failed.", e);
        } finally {
            release(connection);
        }

        return false;
    }
}
