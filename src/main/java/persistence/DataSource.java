package persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private volatile IConnectionPool pool;

    public DataSource(int n) {
        pool = new IConnectionPool(n);
    }

    public Connection getConnection() throws Exception {
        return pool.getConnection();
    }

    public Properties getProperties() {
        return pool.getProperties();
    }
    public Boolean checkout(Connection c) {
        return pool.checkout(c);
    }
}
