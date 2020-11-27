package august.examen;

import august.examen.utils.AugustScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
    double x = 0, y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/views/NewExam.fxml"));
        primaryStage.setTitle("August Examen");
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        AugustScene scene = new AugustScene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
