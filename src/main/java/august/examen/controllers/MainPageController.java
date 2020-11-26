package august.examen.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionListener;

public class MainPageController  {
    public javafx.scene.control.Button searchexambutton;
    public javafx.scene.control.Button newexambutton;


    public void opensearchexamView(ActionEvent actionEvent) {
        Stage currentStage = (Stage) searchexambutton.getScene().getWindow();
        currentStage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SearchExam.fxml"));
    }

    public void opennewexamView(ActionEvent actionEvent) {
        Stage currentStage = (Stage) newexambutton.getScene().getWindow();
        currentStage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NewExam.fxml"));
    }
}
