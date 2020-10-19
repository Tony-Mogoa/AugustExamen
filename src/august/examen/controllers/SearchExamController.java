package august.examen.controllers;

import august.examen.db.DatabaseWrapper;
import august.examen.models.Exam;
import august.examen.models.Question;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.util.Vector;

public class SearchExamController {
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
        });
    }
}
