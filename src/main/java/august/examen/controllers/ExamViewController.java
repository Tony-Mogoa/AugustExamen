package august.examen.controllers;

import august.examen.models.Question;
import august.examen.utils.BluetoothServer;
import august.examen.utils.BluetoothStateUpdater;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Vector;

public class ExamViewController {
    public VBox vbxQuestionLinks;// the side pane item which represent questions
    public Label lblLabel;
    public Label lblContent;
    public ImageView imgBT;
    public Label lblState;
    public Label lblDevice;
    public MenuItem miConnectDevice;
    public ProgressBar progressBar;
    private Vector<Question> questions;
    private Parent clickedLink = null;
    public volatile boolean bluetoothOn = false;

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
            miConnectDevice.setOnAction(e ->{
                BluetoothServer bluetoothServer = new BluetoothServer(progressBar){
                    @Override
                    public void setDeviceName(String deviceName) {
                        Platform.runLater(() -> lblDevice.setText(deviceName));
                    }
                };
                bluetoothServer.setDaemon(true);
                bluetoothServer.start();
            });
        }

        BluetoothStateUpdater bluetoothStateUpdater = new BluetoothStateUpdater(){
            @Override
            public void updateUI(boolean isOn) {
                super.updateUI(isOn);
                if(isOn){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblState.setText("ON:");
                            imgBT.setImage(new Image(getClass().getResource("/img/icons8_bluetooth_2_18px_1.png").toString()));
                        }
                    });
                }
                else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblState.setText("OFF");
                            imgBT.setImage(new Image(getClass().getResource("/img/icons8_bluetooth_2_18px.png").toString()));
                        }
                    });
                }
            }

            @Override
            public void updateBluetoothState(boolean isOn) {
                super.updateBluetoothState(isOn);
                if(isOn){
                    bluetoothOn = true;
                }
                else {
                    bluetoothOn = false;
                }
            }
        };
        bluetoothStateUpdater.setDaemon(true);
        bluetoothStateUpdater.start();
    }
}
