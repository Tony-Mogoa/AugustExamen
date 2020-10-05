package august.examen.controllers;

import august.examen.utils.AugustScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/august/examen/views/newQuestionDialog.fxml"));
            stage.setScene(new AugustScene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void maximize(ActionEvent actionEvent) {
        Stage stage = (Stage)btnMaximize.getScene().getWindow();
        maximized = !maximized;
        if(maximized){
            btnMaximize.setText("Minimize");
        }
        else{
            btnMaximize.setText("Maximize");
        }
        stage.setMaximized(maximized);
    }
}
