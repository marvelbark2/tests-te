package persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionPool {
    public Connection getConnection() throws SQLException, Exception;
    public Boolean checkout(Connection c) throws SQLException;
    public Boolean shutdown();
    public Properties getProperties();
}
