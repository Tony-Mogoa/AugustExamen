package august.examen.controllers;

import august.examen.db.DatabaseWrapper;
import august.examen.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterViewController implements Initializable {
    public TextField txtFirstname;
    public TextField txtSurname;
    public TextField txtLastname;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public TextField txtAdmNumber;
    public ChoiceBox<String> cbxUserType;
    public Button btnSubmit;
    public Label txtError;
    private DatabaseWrapper databaseWrapper;

    public void register() {
        User user = new User(txtAdmNumber.getText(), txtFirstname.getText(), txtSurname.getText(), txtLastname.getText(), cbxUserType.getValue());
        if(txtPassword.getText().equals(txtConfirmPassword.getText())){
            if(user.createUser(databaseWrapper, txtPassword.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
                try {
                    Parent root = loader.load();
                    LoginViewController loginViewController = loader.getController();
                    loginViewController.txtAdmissionNumber.setText(txtAdmNumber.getText());
                    loginViewController.txtPassword.setText(txtPassword.getText());
                    Stage stage = (Stage) btnSubmit.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            txtError.setText("The passwords you entered do not match");
        }
    }

    public void setDatabaseWrapper(DatabaseWrapper databaseWrapper) {
        this.databaseWrapper = databaseWrapper;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxUserType.getItems().addAll("Student", "Lecturer");
        cbxUserType.setValue("Student");
        fakeData();
    }

    public void fakeData(){
        txtFirstname.setText("Tony");
        txtSurname.setText("Mogoa");
        txtLastname.setText("Ombaso");
        txtPassword.setText("mogoa");
        txtConfirmPassword.setText("mogoa");
    }
}
