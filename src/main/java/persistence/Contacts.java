package persistence;

import java.sql.*;
import java.util.*;

public class Contacts {
    private DataSource pool;
    private Properties properties;
    public Contacts(DataSource pool) throws Exception {
        this.pool = pool;
        properties = pool.getProperties();
    }

    public String read(int id) throws Exception {
        Connection connection = pool.getConnection();
        System.out.println(connection.hashCode());
        String result = "";
        PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.READ"));
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            result = rs.getString(1);
        }
        return result;
    }

    public boolean update(int id, String[] values) {
        boolean result = false;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.UPDATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i+1, values[i]);
            }
            preparedStatement.setInt(values.length + 1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {e.printStackTrace();}
        return result;
    }

    public boolean create(String[] values) {
        boolean result = false;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.CREATE"));
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i+1, values[i]);
            }
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {e.printStackTrace();}

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("SQL.DELETE"));
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {e.printStackTrace();}
        return result;
    }

    public Map<List<String>, List<String[]>> all() {
        List<String[]> data = new ArrayList<>();
        List<String> cols = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(properties.getProperty("SQL.ALL"));
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                cols.add(rs.getMetaData().getColumnName(i));
            }
            while (rs.next()) {
                data.add(new String[]{String.valueOf(rs.getInt(1)),rs.getString(2),rs.getString(3), rs.getString(4)});
            }
        } catch (Exception e) {e.printStackTrace();}
        Map<List<String>, List<String[]>> res = new HashMap<>();
        res.put(cols, data);
        return res;
    }

    public boolean closeConnection() throws Exception {
        Connection connection = pool.getConnection();
        return pool.checkout(connection);
    }
}
