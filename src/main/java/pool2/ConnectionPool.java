package pool2;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    public Connection getConnection() throws SQLException;
    public Boolean close(Connection c) throws SQLException;
    public Boolean shutdown();
}
