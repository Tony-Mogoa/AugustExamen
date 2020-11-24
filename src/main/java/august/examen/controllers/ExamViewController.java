package august.examen.controllers;

import august.examen.models.ImageSlider;
import august.examen.models.Question;
import august.examen.utils.BluetoothServer;
import august.examen.utils.BluetoothStateUpdater;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    public ImageView imageView;
    public Button btnLeft;
    public Label lblImgCount;
    public Button btnRight;
    public Label txtCurrentImage;
    public Label lblConnecting;
    private ImageSlider imageSlider;
    private Vector<Question> questions;
    private Parent clickedLink = null;
    public volatile boolean bluetoothOn = false;
    private BluetoothServer bluetoothServer;
    private Question clickedQuestion;

    public void init(Vector<Question> questions){
        this.questions =questions;
        lblLabel.setText(questions.get(0).getLabel());
        lblContent.setText(questions.get(0).getContent());
        clickedQuestion = questions.get(0);
        for (Question question: questions) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuestionLinkView.fxml"));
            try {
                Parent root = loader.load();
                QuestionLinkController questionLinkController = loader.getController();
                questionLinkController.init(question);
                vbxQuestionLinks.getChildren().add(root);
                root.setOnMouseClicked(e -> {
                    //root.getStylesheets().add(getClass().getResource("/august/examen/utils/main.css").toExternalForm());
                    if(clickedLink != null){
                        clickedLink.getStyleClass().remove("clicked");
                        clickedLink.getStyleClass().add("question-link");
                    }
                    clickedLink = root;
                    clickedQuestion = questionLinkController.question;
                    bluetoothServer.setQuestion(clickedQuestion);
                    clickedLink.getStyleClass().add("clicked");
                    clickedLink.getStyleClass().remove("question-link");
                    if(question.isHasParent()){
                        lblLabel.setText(question.getParentLabel() + "(" + question.getLabel() + ")");
                    }
                    else{
                        lblLabel.setText(question.getLabel());
                    }
                    lblContent.setText(question.getContent());
                    initImageSlider();
                    bluetoothServer.setImageSlider(imageSlider);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
//            miConnectDevice.setOnAction(e ->{
//                if(bluetoothServer == null) {
//                    bluetoothServer = new BluetoothServer(progressBar, imageView, lblImgCount, imageSlider, btnLeft, btnRight) {
//                        @Override
//                        public void setDeviceName(String deviceName) {
//                            Platform.runLater(() -> lblDevice.setText(deviceName));
//                        }
//                    };
//                    bluetoothServer.setQuestion(this.clickedQuestion);
//                    bluetoothServer.setDaemon(true);
//                    bluetoothServer.start();
//                }
//            });
        }
        Parent firstQuestion  = (Parent)vbxQuestionLinks.getChildren().get(0);
        clickedLink = firstQuestion;
        firstQuestion.getStyleClass().add("clicked");
        firstQuestion.getStyleClass().remove("question-link");
        int imgCount = clickedQuestion.getPhotosAttached().size();
        initImageSlider();
        bluetoothServer = new BluetoothServer(progressBar, imageView, lblImgCount, imageSlider, btnLeft, btnRight, lblConnecting) {
            @Override
            public void setDeviceName(String deviceName) {
                Platform.runLater(() -> lblDevice.setText(deviceName));
            }
        };
        bluetoothServer.setQuestion(this.clickedQuestion);
        bluetoothServer.setDaemon(true);
        bluetoothServer.start();
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

    private void initImageSlider(){
        int imgCount = clickedQuestion.getPhotosAttached().size();
        imageSlider = new ImageSlider(txtCurrentImage, imgCount);
        btnLeft.setDisable(true);
        if(imgCount <= 1){
            btnRight.setDisable(true);
        }
        else{
            btnRight.setDisable(false);
        }
        String txtImages = imgCount == 1 ? "image" : "images";
        lblImgCount.setText(imgCount +  " " +  txtImages);
        if(imgCount > 0){
            Image image = new Image(clickedQuestion.getPhotosAttached().get(imageSlider.getCurrentImage()).toURI().toString());
            imageView.setImage(image);
        }
        else{
            imageView.imageProperty().set(null);
        }
        btnLeft.setOnMouseClicked(e -> {
            if(imageSlider.getCurrentImage() == 0){
                btnLeft.setDisable(true);
            }
            else{
                btnLeft.setDisable(false);
                imageSlider.decrement();
                if(imageSlider.getCurrentImage() == 0){
                    btnLeft.setDisable(true);
                }
                Image image = new Image(clickedQuestion.getPhotosAttached().get(imageSlider.getCurrentImage()).toURI().toString());
                imageView.setImage(image);
            }
            if(imageSlider.getCurrentImage() == clickedQuestion.getPhotosAttached().size() - 1){
                btnRight.setDisable(true);
            }
            else{
                btnRight.setDisable(false);
            }
            //System.out.println("current image: " + currentImage);
        });
        btnRight.setOnMouseClicked(e -> {
            if(imageSlider.getCurrentImage() == clickedQuestion.getPhotosAttached().size() - 1){
                btnRight.setDisable(true);
            }
            else{
                btnRight.setDisable(false);
                imageSlider.increment();
                if(imageSlider.getCurrentImage() == clickedQuestion.getPhotosAttached().size() - 1){
                    btnRight.setDisable(true);
                }
                Image image = new Image(clickedQuestion.getPhotosAttached().get(imageSlider.getCurrentImage()).toURI().toString());
                imageView.setImage(image);
            }
            if(imageSlider.getCurrentImage() == 0){
                btnLeft.setDisable(true);
            }
            else{
                btnLeft.setDisable(false);
            }
            //System.out.println("current image: " + currentImage);
        });
    }
}
