package com.ita.u1.library.dao;

import com.ita.u1.library.dao.connection_pool.ConnectionPool;

import java.sql.Connection;

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

}

