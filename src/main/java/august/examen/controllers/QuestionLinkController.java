package august.examen.controllers;

import javafx.scene.control.Label;

public class QuestionLinkController {
    public Label lblLabel;
    public Label lblContent;

    public void init(String label, String content){
        setLabel(label);
        setContent(content);
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
