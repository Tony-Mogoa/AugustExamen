package august.examen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewExamController implements Initializable {
    public Button btnCreate;
    public ChoiceBox<String> cbxExamType;

    public void openAddQuestionsView(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) btnCreate.getScene().getWindow();
            currentStage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("/august/examen/views/AddQuestions.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxExamType.getItems().addAll("Ordinary", "Special", "Retake");
        cbxExamType.setValue("Ordinary");
    }
}
