package august.examen.db;

import august.examen.models.Question;

import java.sql.*;
import java.util.Vector;

public class DatabaseWrapper extends Thread{
    private final String connectionUrl = "jdbc:mysql://localhost:3306/august_examen?zeroDateTimeBehavior=convertToNull";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DatabaseWrapper() {
        try {
            connection = DriverManager.getConnection(connectionUrl, "root", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
