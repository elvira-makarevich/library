package com.ita.u1.library.dao.connection_pool;

import com.ita.u1.library.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolImpl implements ConnectionPool {

    private static final ConnectionPool instance = new ConnectionPoolImpl();

    private BlockingQueue<Connection> freeConnection;
    private BlockingQueue<Connection> usedConnection;

    private String driverName;
    private String url;
    private String name;
    private String username;
    private String password;
    private int poolSize;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPoolImpl() {

        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.name = dbResourceManager.getValue(DBParameter.DB_NAME);
        this.username = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));

        try {
            initPoolData();
        } catch (DAOException e) {
            //add log
            throw new RuntimeException("InitPoolData error", e);
        }
    }

    public void initPoolData() throws DAOException {

        try {
            Class.forName(driverName);
            usedConnection = new ArrayBlockingQueue<Connection>(poolSize);
            freeConnection = new ArrayBlockingQueue<Connection>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                freeConnection.add(getNewConnection());
            }

        } catch (ClassNotFoundException e) {
            throw new DAOException("Can't find database driver class", e);

        } catch (SQLException e) {
            throw new DAOException("SQLException in ConnectionPool", e);
        }

    }

    @Override
    public Connection takeConnection() throws DAOException {

        Connection newConnection;
        try {

            newConnection = freeConnection.take();

            if (newConnection != null) {
                if (!newConnection.isValid(0)) {
                    newConnection.close();
                    newConnection = null;
                } else if (freeConnection.size() + usedConnection.size() < poolSize) {
                    freeConnection.add(getNewConnection());
                }
            } else {
                throw new DAOException("Database connection error.");
            }

            usedConnection.add(newConnection);

        } catch (InterruptedException | SQLException e) {
            throw new DAOException("Database connection error.", e);
        }
        return newConnection;
    }


    @Override
    public void releaseConnection(Connection connection) throws DAOException {

        try {
            connection.clearWarnings();
            usedConnection.remove(connection);
            freeConnection.offer(connection);
        } catch (SQLException e) {
            throw new DAOException("Connection Pool: problems returning a connection to free Connection", e);
        }

    }

    private Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(url + name, username, password);
    }
}
