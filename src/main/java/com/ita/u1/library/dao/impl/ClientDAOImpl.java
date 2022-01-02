package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Address;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientDAOImpl extends AbstractDAO implements ClientDAO {

    public ClientDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void add(Client client) {

        Connection connection = take();
        PreparedStatement psClient = null;
        PreparedStatement psClientImage = null;
        ResultSet generatedKeys = null;

        try {
            connection.setAutoCommit(false);
            psClient = connection.prepareStatement("INSERT INTO clients (first_name, last_name, patronymic, passport_number, email, birthday, postcode, country, locality, street, house_number, building, apartment_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            psClientImage = connection.prepareStatement("INSERT INTO clients_images (client_id, image) VALUES (?,?)");

            psClient.setString(1, client.getFirstName());
            psClient.setString(2, client.getLastName());
            psClient.setString(3, client.getPatronymic());
            psClient.setString(4, client.getPassportNumber());
            psClient.setString(5, client.getEmail());
            psClient.setDate(6, Date.valueOf(client.getDateOfBirth()));
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
            generatedKeys = psClient.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("Adding new client failed, no ID obtained.");
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
            close(generatedKeys);
            close(psClientImage, psClient);
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
        PreparedStatement psClient = null;
        ResultSet rs = null;

        try {
            psClient = connection.prepareStatement("SELECT * FROM clients WHERE passport_number=? ");
            psClient.setString(1, passportNumber);

            rs = psClient.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method checkUniquenessPassportNumber() failed.", e);
        } finally {
            close(rs);
            close(psClient);
            release(connection);
        }
        return false;
    }

    @Override
    public boolean checkUniquenessEmail(String email) {

        Connection connection = take();
        PreparedStatement psClient = null;
        ResultSet rs = null;

        try {
            psClient = connection.prepareStatement("SELECT * FROM clients WHERE email=? ");
            psClient.setString(1, email);

            rs = psClient.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method checkUniquenessEmail() failed.", e);
        } finally {
            close(rs);
            close(psClient);
            release(connection);
        }
        return false;
    }

    @Override
    public int getNumberOfClients() {

        int numberOfRecords = 0;

        Connection connection = take();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM clients", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
            rs.last();
            numberOfRecords = rs.getRow();

        } catch (SQLException e) {
            throw new DAOException("Method getNumberOfRecords() failed.", e);
        } finally {
            close(rs);
            close(ps);
            release(connection);
        }
        return numberOfRecords;
    }

    @Override
    public List<Client> getAllClients(int startFromClient, int amountOfClients) {

        List<Client> clients = new ArrayList<>();
        Connection connection = take();
        PreparedStatement psClients = null;
        ResultSet rs = null;

        try {
            psClients = connection.prepareStatement("SELECT * FROM clients order by last_name LIMIT ? OFFSET ?");
            psClients.setInt(1, amountOfClients);
            psClients.setInt(2, startFromClient);

            rs = psClients.executeQuery();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt(1));

                client.setFirstName(rs.getString(2));
                client.setLastName(rs.getString(3));
                client.setDateOfBirth(rs.getDate(7).toLocalDate());
                client.setEmail(rs.getString(6));
                client.setAddress(new Address(rs.getInt(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getInt(12), rs.getString(13), rs.getInt(14)));
                clients.add(client);
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException: method getAllClients() failed.", e);
        } finally {
            close(rs);
            close(psClients);
            release(connection);
        }
        return clients;
    }

    @Override
    public List<Client> findClient(String lastName) {

        List<Client> clients = new ArrayList<>();
        Connection connection = take();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM clients WHERE last_name = ?");
            ps.setString(1, lastName);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt(1));
                    client.setFirstName(rs.getString(2));
                    client.setLastName(rs.getString(3));
                    client.setDateOfBirth(rs.getDate(7).toLocalDate());
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            //log
            throw new DAOException("DAOException: method findClient() failed.", e);
        } finally {
            close(rs);
            close(ps);
            release(connection);
        }

        if (clients.isEmpty()) {
            return Collections.emptyList();
        }
        return clients;
    }

}
