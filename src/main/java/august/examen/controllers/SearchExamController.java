package august.examen.controllers;

import august.examen.db.DatabaseWrapper;
import august.examen.models.Exam;
import august.examen.models.Question;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class SearchExamController implements Initializable {
    public TextField txtCode;
    public Button btnLocate;
    public Vector<Question> questions;
    public ProgressIndicator progressIndicator;
    public Label lblStatusText;

    public void getExam() {
        String code = txtCode.getText();
        progressIndicator.setVisible(true);
        lblStatusText.setVisible(true);
        Task task = new Task<Void>(){
            @Override public Void call() {
                try{
                    Exam exam = new Exam(new DatabaseWrapper(), true);
                    exam.getExam(code);
                    questions = exam.getExamQuestions();
                    exam.getDatabaseWrapper().closeConnection();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(e ->{
            progressIndicator.setVisible(false);
            lblStatusText.setVisible(false);

            //Close the current stage
            Stage currentStage = (Stage) btnLocate.getScene().getWindow();
            currentStage.hide();
            // Here we open the main window
            Stage stage = new Stage();
            //Loading the FXML file of the main ui
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExamView.fxml"));
            try {
                Parent root = loader.load();
                //getting the controller of the main ui file
                ExamViewController examViewController = loader.getController();
                examViewController.init(questions);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("August Examen");
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCode.setText("da33c789-5a89-4625-8c68-7c001c20a4b5");
        //txtCode.setText("0ab0c1aa-2092-4cf7-bd7b-c5f29fa3fa5e");
    }
}
