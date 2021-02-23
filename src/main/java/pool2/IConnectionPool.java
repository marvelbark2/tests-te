package pool2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class IConnectionPool implements ConnectionPool{
    private List<Connection> mountedConnetion;
    private List<Connection> usedConnection;
    private int nPool;

    public IConnectionPool(int nPool) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.nPool = nPool;
        mountedConnetion = new ArrayList<>();
        usedConnection = new ArrayList<>();

        for (int i = 0; i < nPool; i++) {
            try {
                mountedConnetion.add(connectionFactory());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection;
        if(! mountedConnetion.isEmpty()){
            connection = mountedConnetion.remove(mountedConnetion.size() - 1);
            usedConnection.add(connection);
        } else {
            connection = usedConnection.get(0);
            mountedConnetion.remove(connection);
            System.out.println("Ended");
        }

        return connection;
    }

    @Override
    public Boolean close(Connection c) throws SQLException {
        checkout(c);
        c.close();
        return c.isClosed();
    }

    public boolean checkout(Connection c) {
        Boolean status = true;
        if(usedConnection.contains(c)) {
            usedConnection.remove(c);
        }
        if(mountedConnetion.contains(c)){
            mountedConnetion.remove(c);
        }
        return status;
    }
    @Override
    public Boolean shutdown() {
        AtomicReference<Boolean> status = new AtomicReference<>(true);
        usedConnection.stream()
                .forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        status.set(false);
                    }
                });
        usedConnection.clear();
        return status.get();
    }

    private Connection connectionFactory() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ing1_fise", "postgres", "Aqwzsxedc-123");
        return connection;
    }
}
