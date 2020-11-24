package august.examen.controllers;

import august.examen.models.Question;
import javafx.scene.control.Label;

public class QuestionLinkController {
    public Label lblLabel;
    public Label lblContent;
    public Question question;

    public void init(Question question){
        if(question.isHasParent()){
            setLabel(question.getParentLabel() + "(" + question.getLabel() + ")");
        }
        else {
            setLabel(question.getLabel());
        }
        setContent(question.getContent());
        this.question = question;
    }

    public void setLabel(String label){
        lblLabel.setText(label);
    }

    public String getLabel(){
        return lblLabel.getText();
    }

    public void setContent(String content){
        lblContent.setText(content);
    }

    public String getContent(){
        return lblContent.getText();
    }
}
