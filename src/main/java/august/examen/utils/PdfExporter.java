package august.examen.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class PdfExporter {

    private Document document;
    private boolean imageAdded = false;
    public PdfExporter(Stage stage){
        String fileDestination = chooseDirectory(stage);
        if(fileDestination == null){
            //do something
        }
        else {
            PdfWriter writer = null;
            try {
                writer = new PdfWriter(fileDestination + "/ExportedExam.pdf");
                // Creating a PdfDocument
                PdfDocument pdf = new PdfDocument(writer);
                // Creating a Document
                document = new Document(pdf);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String chooseDirectory(Stage stage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory == null){
            return null;
        }else{
            return selectedDirectory.getAbsolutePath();
        }
    }

    public void addImages(ArrayList<File> attachedImages){
        try {
            for (int i = 0; i < attachedImages.size(); i++) {
                ImageData data = ImageDataFactory.create(attachedImages.get(i).toURI().toURL());
                // Creating an Image object
                Image image = new Image(data);
                image.setAutoScale(true);
                AreaBreak areaBreak = new AreaBreak();
                // Adding image to the document
                if(imageAdded)
                    document.add(areaBreak);
                document.add(image);
                imageAdded = true;
                //pick up here
                //System.out.println("Adding image");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void publish(){
        try {
            document.close();
            System.out.println("Published");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("PDF successfully exported to the selected location.");
                alert.show();
            });
        }catch (PdfException e){
            e.printStackTrace();
        }
    }
}
