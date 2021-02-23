package Pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
        ConnectionObject pool = new ConnectionPool(15, "jdbc:postgresql://localhost:5432/ing1_fise", "org.postgresql.Driver");
        for (int i = 0; i < 20; i++) {
            Connection con = pool.checkOut();
            System.out.println(pool.getSize());
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * from address where id = 1");
            while (rs.next()) {
//                System.out.println(rs.getInt(1));
            }
            pool.checkIn(con);
        }
    }
}
