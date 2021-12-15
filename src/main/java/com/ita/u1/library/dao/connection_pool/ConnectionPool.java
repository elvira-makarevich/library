package com.ita.u1.library.dao.connection_pool;

import com.ita.u1.library.dao.exception.DAOException;

import java.sql.Connection;

public interface ConnectionPool {

    Connection takeConnection() throws DAOException;

    void releaseConnection(Connection connection) throws DAOException;

}
