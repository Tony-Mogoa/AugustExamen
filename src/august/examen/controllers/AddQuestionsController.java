package august.examen.controllers;

import august.examen.models.Question;
import august.examen.utils.AugustScene;
import august.examen.views.QuestionEditView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddQuestionsController {
    public Button btnAdd;
    public VBox vbxQuestions;

    public void openAddQuestionsDialog(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/NewQuestion.fxml"));
            Parent root = loader.load();
            NewQuestionController newQuestionController = loader.getController();
            AugustScene scene = new AugustScene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            Question question = newQuestionController.getQuestion();
            QuestionEditView questionEditView = new QuestionEditView(newQuestionController.getQuestion());
            vbxQuestions.getChildren().add(questionEditView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
