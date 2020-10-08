package august.examen.controllers;

import august.examen.models.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewQuestionDialogController {
    public Button btnClose;
    public TextArea txtContent;
    public Button btnAdd;
    public TextField txtLabel;
    public CheckBox cbxAcceptsPhotos;
    private Question question;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage)btnClose.getScene().getWindow();
        stage.close();
    }

    public void addQuestion(ActionEvent actionEvent) {
        question = new Question();
        question.setContent(txtContent.getText());
        question.setLabel(txtLabel.getText());
        question.setAcceptImages(cbxAcceptsPhotos.isSelected());
        Stage stage = (Stage)btnClose.getScene().getWindow();
        stage.close();
    }

    public Question getQuestion() {
        return question;
    }
}
