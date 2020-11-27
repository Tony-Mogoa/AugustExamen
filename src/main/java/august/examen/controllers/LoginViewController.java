package august.examen.controllers;

import august.examen.db.DatabaseWrapper;
import august.examen.models.Exam;
import august.examen.models.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    public TextField txtAdmissionNumber;
    public Button btnLogin;
    public PasswordField txtPassword;
    public Button btnRegister;
    public DatabaseWrapper databaseWrapper;
    public ProgressIndicator progressIndicator;

    public void register() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
            Parent root = loader.load();
            RegisterViewController registerViewController = loader.getController();
            registerViewController.setDatabaseWrapper(databaseWrapper);
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task task = new Task<Void>(){
            @Override public Void call() {
                databaseWrapper = new DatabaseWrapper();
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(e -> {
            progressIndicator.setVisible(false);
            btnLogin.setDisable(false);
            btnRegister.setDisable(false);
        });
    }

    public void login() {
        User user = new User(databaseWrapper, txtAdmissionNumber.getText(), txtPassword.getText());
        if(user.isUserLoggedIn()){
            if(user.getUserType().equals("Student")){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/SearchExamView.fxml"));
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/NewExam.fxml"));
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
