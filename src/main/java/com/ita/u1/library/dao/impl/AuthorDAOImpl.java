package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.dao.connection_pool.ConnectionPoolImpl;
import com.ita.u1.library.dao.exception.DAOException;
import com.ita.u1.library.entity.Author;

import java.sql.*;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    static final ConnectionPool CONNECTION_POOL = ConnectionPoolImpl.getInstance();

    @Override
    public void addAuthor(Author author) throws DAOException {

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

            psImage.close();
            psAuthor.close();

            connection.commit();
            connection.setAutoCommit(true);// Восстановление по умолчанию
        } catch (SQLException e) {
            //log
            e.printStackTrace();
        }

        CONNECTION_POOL.releaseConnection(connection);
    }

    @Override
    public List<Author> findAuthor(int id) throws DAOException {

        Connection connection;
        connection = CONNECTION_POOL.takeConnection();
        List<Author> authors = null;
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT image FROM images WHERE image_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ///ПОСМОТРИ КОНСТРУКТОР!!!
                    authors.add(new Author(rs.getBytes(1)));
                }
                rs.close();
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("TOTAL FIASKO");
        }
        CONNECTION_POOL.releaseConnection(connection);
        return authors;
    }

}
