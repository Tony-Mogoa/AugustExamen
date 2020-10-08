package august.examen.controllers;

import august.examen.models.Question;
import august.examen.utils.AugustScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddQuestionsController{
    public Button btnClose;
    public Button btnAdd;
    public VBox vbxQuestions;
    public Button btnMaximize;
    public AnchorPane ap;
    public ScrollPane sp;
    private boolean maximized = false;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void openAddDialog(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/august/examen/views/newQuestionDialog.fxml"));
            Parent root = loader.load();
            stage.setScene(new AugustScene(root));
            stage.showAndWait();
            NewQuestionDialogController nqdController = loader.getController();
            Question question = nqdController.getQuestion();
            FXMLLoader qVLoader = new FXMLLoader(getClass().getResource("/august/examen/views/questionEditView.fxml"));
            Parent qV = qVLoader.load();
            QuestionEditViewController qevController = qVLoader.getController();
            qevController.init(question, vbxQuestions);
            vbxQuestions.getChildren().add(qV);
            qevController.getAp().setPrefWidth(btnClose.getScene().getWidth() - 20);
            btnClose.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
                qevController.getAp().setPrefWidth(newValue.doubleValue() - 20);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void maximize(ActionEvent actionEvent) {
        Stage stage = (Stage)btnMaximize.getScene().getWindow();
        //Scene scene = btnMaximize.getScene();
        maximized = !maximized;
        if(maximized){
            btnMaximize.setText("Minimize");
        }
        else{
            btnMaximize.setText("Maximize");
        }
        stage.setMaximized(maximized);
    }

    public VBox getVbxQuestions() {
        return vbxQuestions;
    }

    public void setVbxQuestions(VBox vbxQuestions) {
        this.vbxQuestions = vbxQuestions;
    }

    public AnchorPane getAp() {
        return ap;
    }

    public void setAp(AnchorPane ap) {
        this.ap = ap;
    }

    public ScrollPane getSp() {
        return sp;
    }

    public void setSp(ScrollPane sp) {
        this.sp = sp;
    }
}
