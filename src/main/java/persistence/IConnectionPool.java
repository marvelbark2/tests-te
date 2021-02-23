package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class IConnectionPool implements ConnectionPool {
    private List<Connection> mountedConnetion;
    private List<Connection> usedConnection;
    private int nPool;
    private PropertiesReader pReader = PropertiesReader.Instance;

    public IConnectionPool(int nPool) {

        this.nPool = nPool;
        mountedConnetion = new ArrayList<>();
        usedConnection = new ArrayList<>();
        try {
            Class.forName(pReader.DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < nPool; i++) {
            try {
                mountedConnetion.add(connectionFactory());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        Connection connection;
        synchronized (mountedConnetion) {
            if(! mountedConnetion.isEmpty()){
                connection = mountedConnetion.remove(mountedConnetion.size() - 1);
                usedConnection.add(connection);
            } else {
                mountedConnetion.wait(1_200);
                connection = usedConnection.get(0);
                mountedConnetion.remove(connection); // Handling this line ?
            }
        }

        return connection;
    }

    @Override
    public Boolean checkout(Connection c) {
    	Boolean status;
        if(usedConnection.contains(c)) {
            usedConnection.remove(c);
            mountedConnetion.add(c);
            status = true;
        }
        else {
        	status = false;
        }
        return status;
    }
    @Override
    public Boolean shutdown() {
        AtomicReference<Boolean> status = new AtomicReference<>(true);
        List <Connection> allConnections = new ArrayList<>(usedConnection);
        allConnections.addAll(mountedConnetion);
        allConnections.stream()
                .forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        status.set(false);
                    }
                });
        allConnections.clear();
        return status.get();
    }

    @Override
    public Properties getProperties() {
        return pReader.properties;
    }

    private Connection connectionFactory() throws SQLException{
        Connection connection = DriverManager.getConnection(pReader.HOST, pReader.USER, pReader.PASS);
        return connection;
    }
}
