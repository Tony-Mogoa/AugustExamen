package august.examen.controllers;

import august.examen.db.DatabaseWrapper;
import august.examen.models.Exam;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewExamController implements Initializable {
    public Button btnCreate;
    public ChoiceBox<String> cbxExamType;
    public TextField txtSchool;
    public TextField txtCourse;
    public TextField txtUnit;
    public TextField txtHours;
    public TextField txtMinutes;
    public ProgressIndicator progress;

    public void openAddQuestionsView() {
        Exam newExam = new Exam();
        progress.setVisible(true);
        Task task = new Task<Void>(){
            @Override public Void call() {
                try{
                    newExam.setDatabaseWrapper(new DatabaseWrapper());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(e -> {
            progress.setVisible(false);
            newExam.setFaculty(txtSchool.getText());
            newExam.setCourse(txtCourse.getText());
            newExam.setUnit(txtUnit.getText());
            newExam.setExamType(cbxExamType.getValue());
            newExam.setHours(Integer.parseInt(txtHours.getText()));
            newExam.setMinutes(Integer.parseInt(txtMinutes.getText()));

            Stage currentStage = (Stage) btnCreate.getScene().getWindow();
            currentStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddQuestions.fxml"));
            try {
                Parent root = loader.load();
                AddQuestionsController addQuestionsController = loader.getController();
                addQuestionsController.setExam(newExam);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

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
