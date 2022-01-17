package com.ita.u1.library.dao;

import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO {

    private final ConnectionPool connectionPool;

    public AbstractDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected Connection take() {
        Connection connection = connectionPool.takeConnection();
        return connection;
    }

    protected void release(Connection connection) {
        connectionPool.releaseConnection(connection);
    }

    protected void rollback(Connection connection){

        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException("Exception while rollback.", ex);
            }
        }
    }

    protected void setAutoCommitTrue(Connection connection){

        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new DAOException("SQLException in setAutoCommit(true).", ex);
            }
        }
    }

    protected void close(ResultSet... resultSets) {
        if (resultSets != null) {
            for (final ResultSet resultSet : resultSets) {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        throw new DAOException("Closing ResultSet failed.", e);
                    }
                }
            }
        }
    }

    protected void close(PreparedStatement... preparedStatements) {
        if (preparedStatements != null) {
            for (final PreparedStatement preparedStatement : preparedStatements) {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        throw new DAOException("Closing PreparedStatement failed.", e);
                    }
                }
            }
        }
    }

}

