package august.examen.controllers;

import august.examen.models.Exam;
import august.examen.db.DatabaseWrapper;
import august.examen.utils.AugustScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewExamController implements Initializable {

    public Button btnCancel;
    public Button btnCreateExam;
    public TextField txtFaculty;
    public TextField txtCourse;
    public TextField txtUnit;
    public ChoiceBox<String> chbExamType;
    public TextField txtHours;
    public TextField txtMinutes;
    public Label lblStatus;

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void createExam(ActionEvent actionEvent) {
        lblStatus.setText("");
        if(validateFields()){
            lblStatus.setText("Please fill all fields.");
        }
        else{
            Exam exam = new Exam(new DatabaseWrapper());
            exam.setCourse(txtCourse.getText());
            exam.setExamType(chbExamType.getValue());
            exam.setFaculty(txtHours.getText());
            exam.setUnit(txtUnit.getText());
            exam.setHours(Integer.parseInt(txtHours.getText()));
            exam.setMinutes(Integer.parseInt(txtMinutes.getText()));
            if(exam.addNewExam()){
                Stage stage = (Stage) btnCreateExam.getScene().getWindow();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/addQuestions.fxml"));
                    Parent root = loader.load();
                    AugustScene scene = new AugustScene(root);
                    stage.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean validateFields(){
        TextField[] textFields = {txtCourse, txtFaculty, txtHours, txtMinutes, txtUnit};
        boolean isEmpty = false;
        for (TextField textField: textFields) {
            if(textField.getText().equals("")){
                isEmpty = true;
                break;
            }
        }
        return isEmpty;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbExamType.getItems().addAll("Ordinary", "Retake", "Special", "CAT");
        chbExamType.setValue("Ordinary");
        fakeInputs();
    }

    public void fakeInputs(){
        txtUnit.setText("OOP II");
        txtHours.setText("2");
        txtFaculty.setText("SCES");
        txtCourse.setText("ICS");
    }
}
