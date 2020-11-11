package august.examen.utils;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

public class BluetoothStateUpdater extends Thread{
    @Override
    public void run() {
        while(true) {
            try {
                updateUI(LocalDevice.isPowerOn());
                updateBluetoothState(LocalDevice.isPowerOn());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }

    public BluetoothStateUpdater(){

    }

    public void updateUI(boolean isOn){

    }

    public void updateBluetoothState(boolean isOn){

    }
}
