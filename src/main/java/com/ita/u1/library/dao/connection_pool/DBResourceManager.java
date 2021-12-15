package com.ita.u1.library.dao.connection_pool;

import java.util.ResourceBundle;

public class DBResourceManager {

    private final static String DB_RESOURCE = "database/database";

    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_RESOURCE);

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }

}
