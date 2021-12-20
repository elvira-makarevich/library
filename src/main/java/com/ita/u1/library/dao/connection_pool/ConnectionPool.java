package com.ita.u1.library.dao.connection_pool;

import java.sql.Connection;

public interface ConnectionPool {

    Connection takeConnection();
    void releaseConnection(Connection connection);

}
