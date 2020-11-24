package august.examen.utils;

import august.examen.models.ImageSlider;
import august.examen.models.Question;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

public class BluetoothServer extends Thread{

    private LocalDevice localDevice;
    private static final String SERVICE_NAME="ExamenBT";
    private static final String SERVICE_UUID_STRING="8ae8da3987534ced9489a868989bbbc9";
    private final UUID SERVICE_UUID = new UUID(SERVICE_UUID_STRING, false);
    private final String connURL = "btspp://localhost:"+SERVICE_UUID.toString()+";name=" + SERVICE_NAME;
    private StreamConnectionNotifier scn;
    private ProgressBar progressBar = null;
    private ImageView imageView = null;
    private Question question;
    private BtClientSession btClientSession;
    private Label lblImgCount;
    private ImageSlider imageSlider;
    private Button btnLeft;
    private Button btnRight;
    private Label lblConnecting;

    @Override
    public void run() {
        super.run();
        localDevice = null;
        try {
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            createConnection();
        } catch (BluetoothStateException e) {
            e.printStackTrace();
        }
    }

    public BluetoothServer(ProgressBar progressBar, ImageView imageView, Label lblImgCount, ImageSlider imageSlider, Button btnLeft, Button btnRight, Label lblConnecting){
        this.progressBar = progressBar;
        this.imageView = imageView;
        this.lblImgCount = lblImgCount;
        this.imageSlider =imageSlider;
        this.btnLeft = btnLeft;
        this.btnRight = btnRight;
        this.lblConnecting = lblConnecting;
    }

    public void setQuestion(Question question){
        this.question = question;
        if(btClientSession != null){
            btClientSession.setQuestion(question);
        }
    }

    public  Question getQuestion(){
        return this.question;
    }

    private void createConnection(){
        try {
            scn = (StreamConnectionNotifier) Connector.open(connURL);
            //System.out.println("Waiting for connection");
            while(true){
                System.out.println("Waiting for connection");
                StreamConnection sc = scn.acceptAndOpen();
                RemoteDevice rd = RemoteDevice.getRemoteDevice(sc);
                System.out.println("New client connection... " + rd.getFriendlyName(false));
                setDeviceName(rd.getFriendlyName(false));
                btClientSession = new BtClientSession(sc, progressBar, imageView, this.question, lblImgCount, imageSlider, btnLeft, btnRight);
                if(this.question == null){
                    System.out.println("null");
                }
                btClientSession.start();
                lblConnecting.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImageSlider(ImageSlider imageSlider) {
        this.imageSlider = imageSlider;
        btClientSession.setImageSlider(imageSlider);
    }

    public void setDeviceName(String deviceName){

    }

    public void shutdown(){
        try {
            this.scn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
