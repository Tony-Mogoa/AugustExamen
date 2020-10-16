package august.examen.views;

import august.examen.controllers.NewQuestionController;
import august.examen.models.Question;
import august.examen.utils.AugustScene;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionEditView extends VBox {
    private Label lblLabel;
    private Label lblContent;
    private HBox actionBar;
    private CheckBox chbAcceptsImageInput;
    private Button btnAddSubQuestion;
    private Button btnDeleteQuestion;
    private Button btnEditQuestion;
    private VBox vbxSubQuestions;

    public QuestionEditView(Question question){
        QuestionEditView.this.getStylesheets().add(getClass().getResource("/august/examen/utils/main.css").toExternalForm());
        QuestionEditView.this.getStyleClass().add("hbox-question");

        lblLabel = new Label(question.getLabel());
        lblLabel.getStyleClass().add("border-around");
        lblLabel.setWrapText(true);
        lblLabel.getStyleClass().add("question-font");

        lblContent = new Label(question.getContent());
        lblContent.setWrapText(true);
        lblContent.getStyleClass().add("question-font");

        chbAcceptsImageInput = new CheckBox("Accepts image input");
        chbAcceptsImageInput.setSelected(question.isAcceptImages());
        chbAcceptsImageInput.getStyleClass().add("bold-13");

        btnEditQuestion = new Button("Edit");
        btnEditQuestion.getStyleClass().add("btn-primary");
        btnEditQuestion.setOnAction(e ->{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/NewQuestion.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            NewQuestionController newQuestionController = loader.getController();
            newQuestionController.txtLabel.setText(lblLabel.getText());
            newQuestionController.txtContent.setText(lblContent.getText());
            newQuestionController.chbAcceptsImageInput.setSelected(chbAcceptsImageInput.isSelected());
            AugustScene scene = new AugustScene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            Question edittedQuestion = newQuestionController.getQuestion();
            setContent(edittedQuestion);
        });

        btnAddSubQuestion = new Button("Add sub-question");
        btnAddSubQuestion.getStyleClass().add("btn-primary");
        btnAddSubQuestion.setOnAction(e ->{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/NewQuestion.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            NewQuestionController newQuestionController = loader.getController();
            AugustScene scene = new AugustScene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            Question subQuestion = newQuestionController.getQuestion();
            QuestionEditView questionEditView = new QuestionEditView(newQuestionController.getQuestion());
            vbxSubQuestions.getChildren().add(questionEditView);
        });

        btnDeleteQuestion = new Button("Delete");
        btnDeleteQuestion.getStyleClass().add("btn-danger");

        actionBar = new HBox(20, chbAcceptsImageInput, btnEditQuestion, btnAddSubQuestion, btnDeleteQuestion);
        actionBar.getStyleClass().add("action-bar");

        vbxSubQuestions = new VBox(10);
        vbxSubQuestions.getStyleClass().add("sub-question");

        QuestionEditView.this.setSpacing(10);
        QuestionEditView.this.getChildren().addAll(lblLabel, lblContent, vbxSubQuestions, actionBar);
    }

    public void setContent(Question question){
        setLblLabel(question.getLabel());
        setLblContent(question.getContent());
        setChbAcceptsImageInput(question.isAcceptImages());
    }

    public void setLblLabel(String label){
        lblLabel.setText(label);
    }

    public String getLblLabel(){
        return lblLabel.getText();
    }

    public void setLblContent(String content){
        lblContent.setText(content);
    }

    public String getLblContent(){
        return lblContent.getText();
    }

    public void setChbAcceptsImageInput(boolean accepts){
        chbAcceptsImageInput.setSelected(accepts);
    }

    public boolean getChbAcceptsImageInput(){
        return chbAcceptsImageInput.isSelected();
    }
}
