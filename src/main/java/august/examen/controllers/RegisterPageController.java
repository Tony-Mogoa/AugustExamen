package august.examen.controllers;

import august.examen.db.DatabaseWrapper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class RegisterPageController implements Initializable{
    public ChoiceBox<String> choicebox;
    public TextField first_name;
    public TextField mid_name;
    public TextField lastt_name;
    public TextField idd;
    public TextField pnumber;
    public TextField passw;
    public TextField cpassw;
    public DatabaseWrapper databaseWrapper;
    public javafx.scene.control.Button registerbutton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choicebox.getItems().addAll("Student","Lecturer");
    }

    public void registermethod(){
            String firstname = first_name.getText();
            String midname = mid_name.getText();
            String lastname = lastt_name.getText();
            String usertype = choicebox.getValue();
            int id = (Integer.parseInt(idd.getText()));
            int phonenumber = (Integer.parseInt(pnumber.getText()));
            String passwords = passw.getText();
            String cpassword = cpassw.getText();

            String insertfields = "INSERT INTO users (First_Name, Middle_Name, Last_Name, User_Type, ID, Phone_Number, Password, Confirm_password) VALUES(";
            String insertvalues = firstname + "','" + midname + "','" + lastname + "','" + usertype + "','" + id + "','" + phonenumber + "','" + passwords + "','" + cpassword +")";
            String inserttotable = insertfields + insertvalues;

            try{
                PreparedStatement pstmt = databaseWrapper.getConnection().prepareStatement(inserttotable);
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }

    public void registerMethod(javafx.event.ActionEvent actionEvent) {
        registermethod();
        Stage currentStage = (Stage) registerbutton.getScene().getWindow();
        currentStage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainPage.fxml"));
    }
}
