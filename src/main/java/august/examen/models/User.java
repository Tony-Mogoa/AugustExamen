package august.examen.models;

import august.examen.db.DatabaseWrapper;
import august.examen.utils.Hashing;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String admNumber;
    private String firstName;
    private String surName;
    private String lastName;
    private String userType;
    private boolean userLoggedIn = false;

    public User(String admNumber, String firstName, String surName, String lastName, String userType) {
        this.admNumber = admNumber;
        this.firstName = firstName;
        this.surName = surName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public User(DatabaseWrapper databaseWrapper, String userNumber, String userPassword){
        loadUser(databaseWrapper, userNumber, userPassword);
    }

    public String getAdmNumber() {
        return admNumber;
    }

    public void setAdmNumber(String admNumber) {
        this.admNumber = admNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean createUser(DatabaseWrapper databaseWrapper, String password){
        String hashedPassword = Hashing.hash(password);
        String insertSQL = "INSERT INTO user (user_number, user_first_name, user_surname, user_last_name, user_password, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = databaseWrapper.getConnection().prepareStatement(insertSQL);
            pstmt.setString(1, getAdmNumber());
            pstmt.setString(2, getFirstName());
            pstmt.setString(3, getSurName());
            pstmt.setString(4, getLastName());
            pstmt.setString(5, hashedPassword);
            pstmt.setString(6, encodeUserType());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public String encodeUserType(){
        if(getUserType().equals("Student"))
            return "0";
        else
            return "1";
    }

    public String decodeUserType(String codedUserType){
        if(codedUserType.equals("0"))
            return "Student";
        else{
            return "Lecturer";
        }
    }

    public void loadUser(DatabaseWrapper databaseWrapper, String userNumber, String userPassword){
        String hashedPassword = Hashing.hash(userPassword);
        String querySQL = "SELECT * FROM user WHERE user_number = ? AND user_password = ?";
        try {
            PreparedStatement pstmt = databaseWrapper.getConnection().prepareStatement(querySQL);
            pstmt.setString(1, userNumber);
            pstmt.setString(2, hashedPassword);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
               setAdmNumber(rs.getString("user_number"));
               setFirstName(rs.getString("user_first_name"));
               setSurName(rs.getString("user_surname"));
               setLastName(rs.getString("user_last_name"));
               setUserType(decodeUserType(rs.getString("user_type")));
               userLoggedIn = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }
}
