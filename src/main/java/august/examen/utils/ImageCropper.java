package august.examen.utils;

import august.examen.controllers.CropImageViewController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper {
    Stage stage;
    RubberBandSelection rubberBandSelection;
    ImageView imageView;
    ImageView prevImageView;
    String imgUrl;

    public ImageCropper(ImageView prevImageView, String imgUrl){
        this.prevImageView = prevImageView;
        Image image = new Image(new File(imgUrl).toURI().toString());
        this.imgUrl = imgUrl;
        imageView = new ImageView(image);
        imageView.setFitHeight(650);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        stage = new Stage();
        stage.setTitle("Image Crop");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CropImageView.fxml"));
            Parent root = loader.load();
            CropImageViewController cropImageViewController = loader.getController();
            cropImageViewController.group.getChildren().add(imageView);
            rubberBandSelection = new RubberBandSelection(cropImageViewController.group);
            cropImageViewController.btnSave.setOnAction(e ->{
                Bounds selectionBounds = rubberBandSelection.getBounds();
                // show bounds info
                System.out.println( "Selected area: " + selectionBounds);
                // crop the image
                crop( selectionBounds);
                stage.close();
                prevImageView.setImage(new Image(new File(imgUrl).toURI().toString()));
            });
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Stage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crop( Bounds bounds) {
        File file = new File(imgUrl);
        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D( bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage( width, height);
        imageView.snapshot(parameters, wi);

        BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
        BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.OPAQUE);

        Graphics2D graphics = bufImageRGB.createGraphics();
        graphics.drawImage(bufImageARGB, 0, 0, null);

        try {
            ImageIO.write(bufImageRGB, "jpg", file);
            System.out.println( "Image saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics.dispose();
    }

    public static class RubberBandSelection {
        final DragContext dragContext = new DragContext();
        Rectangle rect = new Rectangle();
        Group group;
        public Bounds getBounds() {
            return rect.getBoundsInParent();
        }

        public RubberBandSelection( Group group) {
            this.group = group;
            rect = new Rectangle( 0,0,0,0);
            rect.setStroke(Color.BLUE);
            rect.setStrokeWidth(1);
            rect.setStrokeLineCap(StrokeLineCap.ROUND);
            rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
            group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( event.isSecondaryButtonDown())
                    return;
                // remove old rect
                rect.setX(0);
                rect.setY(0);
                rect.setWidth(0);
                rect.setHeight(0);
                group.getChildren().remove( rect);
                // prepare new drag operation
                dragContext.mouseAnchorX = event.getX();
                dragContext.mouseAnchorY = event.getY();
                rect.setX(dragContext.mouseAnchorX);
                rect.setY(dragContext.mouseAnchorY);
                rect.setWidth(0);
                rect.setHeight(0);
                group.getChildren().add( rect);
            }
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( event.isSecondaryButtonDown())
                    return;
                double offsetX = event.getX() - dragContext.mouseAnchorX;
                double offsetY = event.getY() - dragContext.mouseAnchorY;
                if( offsetX > 0)
                    rect.setWidth( offsetX);
                else {
                    rect.setX(event.getX());
                    rect.setWidth(dragContext.mouseAnchorX - rect.getX());
                }
                if( offsetY > 0) {
                    rect.setHeight( offsetY);
                } else {
                    rect.setY(event.getY());
                    rect.setHeight(dragContext.mouseAnchorY - rect.getY());
                }
            }
        };

        EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( event.isSecondaryButtonDown())
                    return;
            }
        };
        private static final class DragContext {
            public double mouseAnchorX;
            public double mouseAnchorY;
        }
    }
}
