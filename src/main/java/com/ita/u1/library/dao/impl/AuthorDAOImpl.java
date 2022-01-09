package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.AuthorDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.exception.DAOException;
import com.ita.u1.library.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ita.u1.library.util.ConstantParameter.*;

public class AuthorDAOImpl extends AbstractDAO implements AuthorDAO {

    public AuthorDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void addAuthor(Author author) {

        Connection connection = take();
        PreparedStatement psAuthor = null;
        PreparedStatement psImage = null;
        ResultSet generatedKeys = null;

        try {
            connection.setAutoCommit(false);

            psAuthor = connection.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS);
            psImage = connection.prepareStatement(INSERT_AUTHOR_IMAGE);

            psAuthor.setString(1, author.getFirstName());
            psAuthor.setString(2, author.getLastName());

            int affectedRows = psAuthor.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating author failed, no rows affected.");
            }

            generatedKeys = psAuthor.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("Creating author failed, no ID obtained.");
            }

            psImage.setInt(1, author.getId());
            psImage.setBytes(2, author.getImage());
            psImage.executeUpdate();

            connection.commit();

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception while rollback; operation: add author.", ex);
                }
            }
            throw new DAOException("Creating author failed.", e);
        } finally {
            close(generatedKeys);
            close(psAuthor, psImage);
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new DAOException("Creating author failed.", ex);
                }
            }
            release(connection);
        }
    }

    @Override
    public List<Author> findAuthor(String lastName) {

        List<Author> authors = new ArrayList<>();
        Connection connection = take();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(SELECT_AUTHOR_BY_LAST_NAME);
            ps.setString(1, lastName);

            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt(1));
                    author.setFirstName(rs.getString(2));
                    author.setLastName(rs.getString(3));
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("SQLException when finding the authors.", e);
        } finally {
            close(rs);
            close(ps);
            release(connection);
        }

        return authors;
    }

    @Override
    public Optional<Author> findAuthorImage(int id) {

        Author author = new Author();
        Optional<Author> optionalAuthor;
        Connection connection = take();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(SELECT_AUTHOR_IMAGE);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    author.setImage(rs.getBytes(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("SQLException while finding the author's image.", e);
        } finally {
            close(rs);
            close(ps);
            release(connection);
        }

        optionalAuthor = Optional.of(author);
        return optionalAuthor;
    }

}
