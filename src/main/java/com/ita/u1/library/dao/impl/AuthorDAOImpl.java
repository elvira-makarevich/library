package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.dao.connection_pool.ConnectionPoolImpl;
import com.ita.u1.library.dao.exception.DAOException;
import com.ita.u1.library.entity.Author;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class AuthorDAOImpl implements AuthorDAO {

    static final ConnectionPool CONNECTION_POOL = ConnectionPoolImpl.getInstance();

    @Override
    public void addAuthor(Author author) throws DAOException {

        Connection connection;
        connection = CONNECTION_POOL.takeConnection();

        try {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO images (image) VALUES (?)");
            ps.setBytes(1, author.getImage());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        CONNECTION_POOL.releaseConnection(connection);
    }

    @Override
    public Author findAuthor(int id) throws DAOException {

        Connection connection;
        connection = CONNECTION_POOL.takeConnection();
        Author author = null;
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT image FROM images WHERE image_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    author = new Author(rs.getBytes(1));

                }
                rs.close();
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("TOTAL FIASKO");
        }
        CONNECTION_POOL.releaseConnection(connection);
        return author;
    }

}
