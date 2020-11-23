package august.examen.utils;

import javafx.application.Platform;
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

    public BtClientSession(StreamConnection sc, ProgressBar progressBar, ImageView imageView) {
        this.sc = sc;
        this.progressBar = progressBar;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        try {
            InputStream is = sc.openInputStream();

            while(true) {
                Long fileSize = getFileLength(is);
                String fileName = getFileName(is);
                System.out.println("File name:" + fileName + " size: " + fileSize);
                DataInputStream dis = new DataInputStream(is);
                File file = new File(fileName);
                // Create an image.jpg file locally to receive the image file data from the mobile client.
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
                    double progress = fos.getChannel().position() / (double)fileSize2;
                    System.out.println("Progress " + fos.getChannel().position() + " /" + fileSize2 +  "=" + progress);
                    Platform.runLater(() -> progressBar.setProgress(progress));
                }
                fos.flush();
                fos.close();
                Image image = new Image(file.toURI().toString());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImage(image);
                        progressBar.setVisible(false);
                        progressBar.setPrefHeight(0);
                    }
                });
            }
//            int c = 0;
//
//
//            System.out.println("Start reading data...");
//            double bytesReceived = 0;
//            while (true) {
//                //System.out.println("cnt..\n");
//                c = is.read(buffer);
//                if (c == -1) {
//                    System.out.println("End of reading data");
//                    break;
//                } else {
//                    double progress = (++bytesReceived * 1024)/fileSize;
//                    System.out.println(progress);
//                    Platform.runLater(() -> progressBar.setProgress(progress));
//                    fos.write(buffer, 0, c);
//                }
//                System.out.println("loop runnig");
//            }
            //bis.close();
            //System.out.println("everything flushed count :" +  count);
            //sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private Long getFileLength(InputStream inStream) {
        try {
            DataInputStream dataInputStream = new DataInputStream(inStream);
            Long size = dataInputStream.readLong();
            return size;
        } catch (IOException u) {
            u.printStackTrace();
            return -1L;
        }
    }

    private String getFileName(InputStream inputStream){
        try {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String fileName = dataInputStream.readUTF();
            return fileName;
        } catch (IOException u) {
            u.printStackTrace();
            return null;
        }
    }


}
