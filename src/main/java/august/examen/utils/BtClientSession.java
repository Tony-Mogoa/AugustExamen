package august.examen.utils;

import august.examen.models.ImageSlider;
import august.examen.models.Question;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.microedition.io.StreamConnection;
import java.io.*;
import java.net.Inet4Address;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;

public class BtClientSession extends Thread{
    private StreamConnection sc;
    private ProgressBar progressBar = null;
    private ImageView imageView = null;
    private Question question;
    private Label lblImgCount;
    private ImageSlider imageSlider;
    private Button btnLeft;
    private Button btnRight;

    public BtClientSession(StreamConnection sc, ProgressBar progressBar, ImageView imageView, Question question, Label lblImgCount, ImageSlider imageSlider, Button btnLeft, Button btnRight) {
        this.sc = sc;
        this.progressBar = progressBar;
        this.imageView = imageView;
        this.question = question;
        this.lblImgCount = lblImgCount;
        this.imageSlider = imageSlider;
        this.btnLeft = btnLeft;
        this.btnRight = btnRight;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setImageSlider(ImageSlider imageSlider) {
        this.imageSlider = imageSlider;
    }

    @Override
    public void run() {
        try {
            InputStream is = sc.openInputStream();
            while(true) {
                try {
                    Long fileSize = getFileLength(is);
                    String fileName = getFileName(is);
                    //System.out.println("File name:" + fileName + " size: " + fileSize);
                    DataInputStream dis = new DataInputStream(is);
                    // Create an image.jpg file locally to receive the image file data from the mobile client.
                    File dir = new File(System.getProperty("user.home") + "/august_examen");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir.getCanonicalPath() + "/" + fileName);
                    question.getPhotosAttached().add(file);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int n = 0;
                    Long fileSize2 = fileSize;
                    int count = 0;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisible(true);
                            progressBar.setPrefHeight(10);
                        }
                    });
                    while (fileSize > 0 && (n = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                        count++;
                        fos.write(buffer, 0, n);
                        fileSize -= n;
                        double progress = fos.getChannel().position() / (double) fileSize2;
                        //System.out.println("Progress " + fos.getChannel().position() + " /" + fileSize2 +  "=" + progress);
                        Platform.runLater(() -> progressBar.setProgress(progress));
                    }
                    fos.flush();
                    fos.close();
                    Image image = new Image(file.toURI().toString());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int imgCount = question.getPhotosAttached().size();
                            String txtImages = imgCount == 1 ? "image" : "images";
                            lblImgCount.setText(imgCount + " " + txtImages);
                            imageView.setImage(image);
                            imageSlider.setCurrentImage(question.getPhotosAttached().size() - 1);
                            if (imageSlider.getCurrentImage() == 0) {
                                btnLeft.setDisable(true);
                            } else {
                                btnLeft.setDisable(false);
                            }
                            if (imageSlider.getCurrentImage() == imgCount - 1) {
                                btnRight.setDisable(true);
                            } else {
                                btnRight.setDisable(false);
                            }
                            progressBar.setVisible(false);
                            progressBar.setPrefHeight(0);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private Long getFileLength(InputStream inStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inStream);
        Long size = dataInputStream.readLong();
        return size;
    }

    private String getFileName(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String fileName = dataInputStream.readUTF();
        return fileName;
    }


}
