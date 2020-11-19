package august.examen.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class AugustScene extends Scene {
    double x = 0, y = 0;
    public AugustScene(Parent parent) {
        super(parent);
        AugustScene.this.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
//        this.setOnMousePressed(event ->{
//            x = event.getSceneX();
//            y = event.getSceneY();
//        });
//
//        this.setOnMouseDragged(mouseEvent -> {
//            this.getWindow().setX(mouseEvent.getScreenX() - x);
//            this.getWindow().setY(mouseEvent.getScreenY() - y);
//        });
    }

}
