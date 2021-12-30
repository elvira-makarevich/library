package com.ita.u1.library.dao;

import com.ita.u1.library.dao.connection_pool.ConnectionPool;

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

    protected void close(ResultSet... resultSets) {
        if (resultSets != null) {
            for (final ResultSet resultSet : resultSets) {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        //log
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
                        //log
                    }
                }
            }
        }
    }

}

