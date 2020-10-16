package august.examen.controllers;

import august.examen.models.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class NewExamController implements Initializable {
    public Button btnCreate;
    public ChoiceBox<String> cbxExamType;
    public TextField txtSchool;
    public TextField txtCourse;
    public TextField txtUnit;
    public TextField txtHours;
    public TextField txtMinutes;

    public void openAddQuestionsView() {
        try {
            UUID uniqueKey = UUID.randomUUID();

            Exam newExam = new Exam();
            newExam.setExamId(uniqueKey.toString());
            newExam.setFaculty(txtSchool.getText());
            newExam.setCourse(txtCourse.getText());
            newExam.setUnit(txtUnit.getText());
            newExam.setExamType(cbxExamType.getValue());
            newExam.setHours(Integer.parseInt(txtHours.getText()));
            newExam.setMinutes(Integer.parseInt(txtMinutes.getText()));

            Stage currentStage = (Stage) btnCreate.getScene().getWindow();
            currentStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/AddQuestions.fxml"));
            Parent root = loader.load();
            AddQuestionsController addQuestionsController = loader.getController();
            addQuestionsController.setExam(newExam);

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
        fakeData();
    }

    public void fakeData(){
        txtSchool.setText("FIT");
        txtCourse.setText("ICS");
        txtUnit.setText("OOP II");
        txtHours.setText("2");
        txtMinutes.setText("0");
    }
}
