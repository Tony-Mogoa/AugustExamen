package august.examen.controllers;

import august.examen.models.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class QuestionEditViewController{
    public TextField txtLabel;
    public TextArea txtContent;
    public CheckBox cbxAcceptsPhotos;
    public Button btnDelete;
    public Question question;
    public AnchorPane getAp() {
        return ap;
    }

    public AnchorPane ap;

    public void deleteQuestion(ActionEvent actionEvent) {

    }

    public void init(Question question, VBox vbxQuestions){
        this.question = question;
        setCbxAcceptsPhotos(question.isAcceptImages());
        setTxtContent(question.getContent());
        setTxtLabel(question.getLabel());
    }

    public String getTxtLabel() {
        return txtLabel.getText();
    }

    public void setTxtLabel(String label) {
        this.txtLabel.setText(label);
    }

    public String getTxtContent() {
        return txtContent.getText();
    }

    public void setTxtContent(String content) {
        this.txtContent.setText(content);
    }

    public boolean getCbxAcceptsPhotos() {
        return cbxAcceptsPhotos.isSelected();
    }

    public void setCbxAcceptsPhotos(boolean acceptsPhotos) {
        this.cbxAcceptsPhotos.setSelected(acceptsPhotos);
    }
}
