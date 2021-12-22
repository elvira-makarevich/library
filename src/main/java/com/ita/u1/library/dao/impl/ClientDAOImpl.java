package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.ClientDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;

public class ClientDAOImpl extends AbstractDAO  implements ClientDAO {

    public ClientDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

}
