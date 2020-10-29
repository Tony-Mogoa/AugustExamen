package august.examen.utils;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

public class BluetoothStateUpdater extends Thread{
    public LocalDevice localDevice;

    @Override
    public void run() {
        while(true)
        try {
            localDevice = LocalDevice.getLocalDevice();
            Thread.sleep(3000);
            //System.out.println("Polling");
        } catch (BluetoothStateException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BluetoothStateUpdater(){

    }
}
