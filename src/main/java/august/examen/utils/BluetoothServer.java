package august.examen.utils;

import javafx.scene.control.ProgressBar;
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

    public BluetoothServer(ProgressBar progressBar, ImageView imageView){
        this.progressBar = progressBar;
        this.imageView = imageView;
    }

    private void createConnection(){
        try {
            scn = (StreamConnectionNotifier) Connector.open(connURL);
            //System.out.println("Waiting for connection");
            while(true){
                System.out.println("Waiting for connection");
                StreamConnection sc = scn.acceptAndOpen();
                System.out.println("Waiting for connection2");
                RemoteDevice rd = RemoteDevice.getRemoteDevice(sc);
                System.out.println("New client connection... " + rd.getFriendlyName(false));
                setDeviceName(rd.getFriendlyName(false));
                new BtClientSession(sc, progressBar, imageView).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
