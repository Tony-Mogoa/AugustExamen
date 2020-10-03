package august.examen.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddQuestionsController {
    public Button btnClose;
    public Button btnAdd;
    public VBox vbxQuestions;
    public Button btnMaximize;
    private boolean maximized = false;

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void openAddDialog(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setScene(new Scene(new VBox()));
        stage.show();
    }

    public void maximize(ActionEvent actionEvent) {
        Stage stage = (Stage)btnMaximize.getScene().getWindow();
        maximized = !maximized;
        if(maximized){
            btnMaximize.setText("Minimize");
        }
        stage.setMaximized(maximized);
    }
}
