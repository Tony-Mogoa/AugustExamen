package august.examen.views;

import august.examen.controllers.NewQuestionController;
import august.examen.db.DatabaseWrapper;
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
import java.util.Vector;

public class QuestionEditView extends VBox {
    private final Label lblLabel;
    private final Label lblContent;
    private final HBox actionBar;
    private final CheckBox chbAcceptsImageInput;
    private final Button btnAddSubQuestion;
    private final Button btnDeleteQuestion;
    private final Button btnEditQuestion;
    public VBox vbxSubQuestions;
    private Question question;
    private int questionIndex;
    private int subQuestionCount = 0;
    private Vector<Question> questions;

    public QuestionEditView(Question question, Vector<Question> questions, DatabaseWrapper databaseWrapper){
        this.question = question;
        questionIndex = questions.size();
        this.questions = questions;
        this.question.setHasChildren(false);
        questions.add(this.question);
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
        chbAcceptsImageInput.setDisable(true);

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
            newQuestionController.setDatabaseWrapper(databaseWrapper);
            newQuestionController.txtLabel.setText(lblLabel.getText());
            newQuestionController.txtContent.setText(lblContent.getText());
            newQuestionController.chbAcceptsImageInput.setSelected(chbAcceptsImageInput.isSelected());
            newQuestionController.btnAdd.setText("Save");
            AugustScene scene = new AugustScene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            try {
                Question edittedQuestion = newQuestionController.getQuestion();
                setContent(edittedQuestion);
            }catch (NullPointerException ex){

            }
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
            newQuestionController.setDatabaseWrapper(databaseWrapper);
            AugustScene scene = new AugustScene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            try {
                Question subQuestion = newQuestionController.getQuestion();
                subQuestionCount++;
                subQuestion.setParentId(question.getQuestionId());
                subQuestion.setHasParent(true);
                subQuestion.setExamId(question.getExamId());
                subQuestion.setOrder(subQuestionCount);
                QuestionEditView questionEditView = new QuestionEditView(newQuestionController.getQuestion(), this.questions, databaseWrapper);
                vbxSubQuestions.getChildren().add(questionEditView);
                this.question.setHasChildren(true);
            } catch (NullPointerException ex){
                //ex.printStackTrace();
            }
        });

        btnDeleteQuestion = new Button("Delete");
        btnDeleteQuestion.getStyleClass().add("btn-danger");
        btnDeleteQuestion.setOnAction(e -> {
            questions.remove(questionIndex);
            VBox parent = (VBox) QuestionEditView.this.getParent();
            parent.getChildren().remove(QuestionEditView.this);
        });

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
        this.question.setLabel(question.getLabel());
        this.question.setContent(question.getContent());
        this.question.setAcceptImages(question.isAcceptImages());
        this.questions.set(questionIndex, this.question);
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

    public Question getQuestion() {
        return question;
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
