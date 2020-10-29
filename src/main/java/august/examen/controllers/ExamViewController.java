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
    private Parent clickedLink = null;

    public void init(Vector<Question> questions){
        this.questions =questions;
        lblLabel.setText(questions.get(0).getLabel());
        lblContent.setText(questions.get(0).getContent());
        for (Question question: questions) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuestionLinkView.fxml"));
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
                    //root.getStylesheets().add(getClass().getResource("/august/examen/utils/main.css").toExternalForm());
                    if(clickedLink != null){
                        clickedLink.getStyleClass().remove("clicked");
                        clickedLink.getStyleClass().add("question-link");
                    }
                    clickedLink = root;
                    clickedLink.getStyleClass().add("clicked");
                    clickedLink.getStyleClass().remove("question-link");
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
