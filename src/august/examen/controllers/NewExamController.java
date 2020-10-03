package august.examen.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class NewExamController{

    public Button btnCancel;

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }
}
