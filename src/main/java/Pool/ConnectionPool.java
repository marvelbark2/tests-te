package Pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool extends ConnectionObject{
    private String url;
    public ConnectionPool(int n, String url, String driver) {
        super(n);
        this.url = url;

        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Connection create() throws SQLException {
        return DriverManager.getConnection(url,"postgres", "Aqwzsxedc-123");
    }

    public void expire(Connection o) {
        try {
            o.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(Connection con) throws SQLException {
        return con.isClosed();
    }
}
