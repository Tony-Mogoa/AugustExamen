package august.examen.controllers;

import august.examen.models.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Vector;

public class ExamViewController {
    public VBox vbxQuestionLinks;// the side pane item which represent questions
    public Label lblLabel;
    public Label lblContent;
    private Vector<Question> questions;

    public void init(Vector<Question> questions){
        this.questions =questions;
        for (Question question: questions) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/QuestionLinkView.fxml"));
            try {
                Parent root = loader.load();
                QuestionLinkController questionLinkController = loader.getController();
                if(question.isHasParent()){
                    questionLinkController.init(question.getParentLabel() + "(" + question.getLabel() + ")", question.getContent());
                }
                else {
                    questionLinkController.init(question.getLabel(), question.getContent());
                }
                vbxQuestionLinks.getChildren().add(root);
                root.setOnMouseClicked(e -> {
                    if(question.isHasParent()){
                        lblLabel.setText(question.getParentLabel() + "(" + question.getLabel() + ")");
                    }
                    else{
                        lblLabel.setText(question.getLabel());
                    }
                    lblContent.setText(question.getContent());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
