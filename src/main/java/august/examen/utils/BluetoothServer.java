package august.examen.utils;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

public class BluetoothServer {

    private LocalDevice localDevice;
    private static final String SERVICE_NAME="ExamenBT";
    private static final String SERVICE_UUID_STRING="8ae8da3987534ced9489a868989bbbc9";
    private final UUID SERVICE_UUID = new UUID(SERVICE_UUID_STRING, false);
    private final String connURL = "btspp://localhost:"+SERVICE_UUID.toString()+";name=" + SERVICE_NAME;
    private StreamConnectionNotifier scn;

    public BluetoothServer(){
        localDevice = null;
        try {
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            createConnection();
        } catch (BluetoothStateException e) {
            e.printStackTrace();
        }
    }

    private void createConnection(){
        try {
            scn = (StreamConnectionNotifier) Connector.open(connURL);
            while(true){
                StreamConnection sc = scn.acceptAndOpen();
                RemoteDevice rd = RemoteDevice.getRemoteDevice(sc);
                System.out.println("New client connection... " + rd.getFriendlyName(false));
                new BtClientSession(sc).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown(){
        try {
            this.scn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
