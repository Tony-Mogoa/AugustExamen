package august.examen.utils;

import javax.microedition.io.StreamConnection;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;

public class BtClientSession extends Thread{
    private StreamConnection sc;

    public BtClientSession(StreamConnection sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream bis = new BufferedInputStream(sc.openInputStream());

            // Create an image.jpg file locally to receive the image file data from the mobile client.
            FileOutputStream fos = new FileOutputStream("image.jpg");

            int c = 0;
            byte[] buffer = new byte[1024];

            System.out.println("Start reading data...");
            while (true) {
                System.out.println("cnt..\n");
                c = bis.read(buffer);
                if (c == -1) {
                    System.out.println("End of reading data");
                    break;
                } else {
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
}
