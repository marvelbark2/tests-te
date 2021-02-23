package pool2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
        ConnectionPool pool = new IConnectionPool(15);
        for (int i = 0; i < 20; i++) {
            Connection con = pool.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from address where id = 1");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }

    }
}
