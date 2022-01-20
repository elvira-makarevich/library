package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Address;
import com.ita.u1.library.entity.Client;
import com.ita.u1.library.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ita.u1.library.util.ConstantParameter.*;

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
            psClient = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS);
            psClientImage = connection.prepareStatement(INSERT_CLIENT_IMAGE);

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
            rollback(connection);
            throw new DAOException("Adding book to database failed.", e);
        } finally {
            close(generatedKeys);
            close(psClientImage, psClient);
            setAutoCommitTrue(connection);
            release(connection);
        }
    }

    @Override
    public boolean checkUniquenessPassportNumber(String passportNumber) {

        Connection connection = take();
        PreparedStatement psClient = null;
        ResultSet rs = null;

        try {
            psClient = connection.prepareStatement(SELECT_CLIENT_BY_PASSPORT_NUMBER);
            psClient.setString(1, passportNumber);

            rs = psClient.executeQuery();

            if (rs.next()) {
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
            psClient = connection.prepareStatement(SELECT_CLIENT_BY_EMAIL);
            psClient.setString(1, email);

            rs = psClient.executeQuery();

           if (rs.next()) {
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
            ps = connection.prepareStatement(SELECT_ALL_CLIENTS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
            psClients = connection.prepareStatement(SELECT_LIMIT_CLIENTS);
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
            ps = connection.prepareStatement(SELECT_CLIENTS_BY_LAST_NAME);
            ps.setString(1, lastName);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt(1));
                    client.setFirstName(rs.getString(2));
                    client.setLastName(rs.getString(3));
                    client.setEmail(rs.getString(6));
                    client.setDateOfBirth(rs.getDate(7).toLocalDate());
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException: method findClient() failed.", e);
        } finally {
            close(rs);
            close(ps);
            release(connection);
        }
        return clients;
    }
}
