package august.examen.controllers;

import august.examen.models.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class QuestionEditViewController{
    public CheckBox cbxAcceptsPhotos;
    public Button btnDelete;
    public Question question;
    public VBox parentVBox;
    public AnchorPane topAp;
    public AnchorPane lowerAp;
    public Label lblContent;
    public Label lblLabel;

    public VBox getParentVBox() {
        return parentVBox;
    }

    public void deleteQuestion(ActionEvent actionEvent) {

    }

    public AnchorPane getTopAp() {
        return topAp;
    }

    public AnchorPane getLowerAp() {
        return lowerAp;
    }

    public void init(Question question, VBox vbxQuestions){
        this.question = question;
        setCbxAcceptsPhotos(question.isAcceptImages());
        setLblContent(question.getContent());
        setLblLabel(question.getLabel());
        lblContent.setMinHeight(Region.USE_PREF_SIZE);
        lowerAp.setMinWidth(Region.USE_PREF_SIZE);
    }

    public String getLblLabel() {
        return lblLabel.getText();
    }

    public void setLblLabel(String label) {
        this.lblLabel.setText(label);
    }

    public String getLblContent() {
        return lblContent.getText();
    }

    public void setLblContent(String content) {
        this.lblContent.setText(content);
    }
    public Label getLabelContent(){
        return this.lblContent;
    }

    public boolean getCbxAcceptsPhotos() {
        return cbxAcceptsPhotos.isSelected();
    }

    public void setCbxAcceptsPhotos(boolean acceptsPhotos) {
        this.cbxAcceptsPhotos.setSelected(acceptsPhotos);
    }
}
