package august.examen.controllers;

import august.examen.models.Exam;
import august.examen.models.Question;
import august.examen.utils.AugustScene;
import august.examen.views.QuestionEditView;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

public class AddQuestionsController {
    public Button btnAdd;
    public VBox vbxQuestions;
    public Exam exam;
    public Button btnSave;
    public ProgressIndicator progressIndicator;
    private int questionCount = 0;
    private Vector<Question> questions = new Vector<>();

    public void openAddQuestionsDialog() {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/NewQuestion.fxml"));
            Parent root = loader.load();
            NewQuestionController newQuestionController = loader.getController();
            newQuestionController.setDatabaseWrapper(exam.databaseWrapper);
            AugustScene scene = new AugustScene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            questionCount++;
            Question question = newQuestionController.getQuestion();
            question.setExamId(exam.getExamId());
            question.setParentId("");
            question.setHasParent(false);
            question.setOrder(questionCount);

            QuestionEditView questionEditView = new QuestionEditView(newQuestionController.getQuestion(), questions, exam.databaseWrapper);
            vbxQuestions.getChildren().add(questionEditView);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void saveExam() {
        progressIndicator.setVisible(true);
        Task task = new Task<Void>() {
            @Override public Void call() {
                try{
                    exam.saveExam();
                    for (Question question: questions) {
                        question.saveQuestion();
                    }
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
            exam.databaseWrapper.closeConnection();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });
    }

}
