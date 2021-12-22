package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.dao.connection_pool.ConnectionPoolImpl;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.exception.DAONoSuchImageAuthorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuthorDAOImpl implements AuthorDAO {

    static final ConnectionPool CONNECTION_POOL = ConnectionPoolImpl.getInstance();

    @Override
    public void addAuthor(Author author) {
        System.out.println(author.getFirstName());
        Connection connection = CONNECTION_POOL.takeConnection();

        try (PreparedStatement psAuthor = connection.prepareStatement("INSERT INTO authors (first_name, last_name) VALUES (?,?) ", Statement.RETURN_GENERATED_KEYS); PreparedStatement psImage = connection.prepareStatement("INSERT INTO authors_images (author_id, image) VALUES (?,?)")) {

            connection.setAutoCommit(false);
            psAuthor.setString(1, author.getFirstName());
            psAuthor.setString(2, author.getLastName());

            int affectedRows = psAuthor.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating author failed, no rows affected.");
            }
            try (ResultSet generatedKeys = psAuthor.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating author failed, no ID obtained.");
                }
            }

            psImage.setInt(1, author.getId());
            psImage.setBytes(2, author.getImage());
            psImage.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            //log
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: add author.", ex);
                }
            throw new DAOException("Creating author failed.", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);// Восстановление по умолчанию
                } catch (SQLException ex) {
                    throw new DAOException("Creating author failed.", ex);
                }
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public List<Author> findAuthor(String lastName) {

        Connection connection;
        connection = CONNECTION_POOL.takeConnection();
        List<Author> authors = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM authors WHERE last_name = ?")) {

            ps.setString(1, lastName);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String firstName = rs.getString(2);
                    String lastNameFound = rs.getString(3);
                    authors.add(new Author(id, firstName, lastNameFound));
                }
                rs.close();
            }

        } catch (SQLException e) {
            //log
            throw new DAOException("SQLException when finding the authors.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }

        if (authors.isEmpty()) {
            return Collections.emptyList();
        }
        return authors;
    }

    @Override
    public Author findAuthorImage(int id) {

        Connection connection;
        connection = CONNECTION_POOL.takeConnection();
        Author author = null;
        Optional<Author> optionalAuthor;

        try (PreparedStatement ps = connection.prepareStatement("SELECT image FROM authors_images WHERE author_id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    author.setImage(rs.getBytes(1));
                }
                rs.close();
            }

        } catch (SQLException e) {
            //log
            throw new DAOException("SQLException when finding the author's image.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }

        optionalAuthor = Optional.of(author);
        author = optionalAuthor.orElseThrow(() -> new DAONoSuchImageAuthorException("Image of the author with the id does not exist."));
        return author;
    }

}
