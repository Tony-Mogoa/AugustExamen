package august.examen.utils;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import javax.microedition.io.StreamConnection;
import java.io.*;
import java.net.Inet4Address;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;

public class BtClientSession extends Thread{
    private StreamConnection sc;
    private ProgressBar progressBar = null;

    public BtClientSession(StreamConnection sc, ProgressBar progressBar) {
        this.sc = sc;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        try {
            InputStream is = sc.openInputStream();
            Long size = processBytes(is);
            BufferedInputStream bis = new BufferedInputStream(is);


            // Create an image.jpg file locally to receive the image file data from the mobile client.
            FileOutputStream fos = new FileOutputStream("image.jpg");

            int c = 0;
            byte[] buffer = new byte[1024];

            System.out.println("Start reading data...");
            double bytesReceived = 0;
            while (true) {
                //System.out.println("cnt..\n");
                c = bis.read(buffer);
                if (c == -1) {
                    //System.out.println("End of reading data");
                    break;
                } else {
                    double progress = (++bytesReceived * 1024)/size;
                    System.out.println(progress);
                    Platform.runLater(() -> progressBar.setProgress(progress));
                    fos.write(buffer, 0, c);
                }
            }
            fos.flush();
            fos.close();
            bis.close();
            //sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Long processBytes(InputStream inStream) {
        try {
            DataInputStream dataInputStream = new DataInputStream(inStream);
            Long size = dataInputStream.readLong();
            return size;
        } catch (IOException u) {
            u.printStackTrace();
            return -1L;
        }
    }


}
