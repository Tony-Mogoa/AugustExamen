package august.examen.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewQuestionDialogController {
    public Button btnClose;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage)btnClose.getScene().getWindow();
        stage.close();
    }

    public void addQuestion(ActionEvent actionEvent) {
    }
}
