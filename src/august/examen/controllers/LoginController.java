package august.examen.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    public TextField username;
    public TextField password;
    public Button btnLogin;

    public void login(ActionEvent actionEvent) {
        if(username.getText() != null && password.getText() != null){
            System.out.println(username.getText() + " " + password.getText());
            btnLogin.setText("Logged In");
        }
    }
}
