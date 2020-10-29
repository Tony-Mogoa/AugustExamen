package august.examen.db;

import august.examen.models.Question;

import java.sql.*;
import java.util.Vector;

public class DatabaseWrapper{
    //private final String connectionUrl = "jdbc:mysql://remotemysql.com:3306/CvDaJEZqm6?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&verifyServerCertificate=false&useSSL=true";
    private final String connectionUrl = "jdbc:mysql://localhost/august_examen?zeroDateTimeBehavior=convertToNull";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DatabaseWrapper() {
        try {
            //connection = DriverManager.getConnection(connectionUrl, "CvDaJEZqm6", "xuLs9ErMn8");
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
