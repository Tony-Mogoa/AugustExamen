package august.examen.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown{
    private Timer timer;
    private int seconds;//duration in seconds
    private Label lblTimeCountdown;

    public Countdown(int seconds, Label lblTimeCountDown){
        this.seconds = seconds;
        timer = new Timer();
        this.lblTimeCountdown = lblTimeCountDown;
        startCountdown();
    }

    public void startCountdown() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> lblTimeCountdown.setText(formatTime()));
                seconds--;
                if (seconds < 0) {
                    timer.cancel();
                    Platform.runLater(() -> lblTimeCountdown.setText("Time is up"));
                }
            }
        }, 1000, 1000);
    }

    private String formatTime(){
        int remainderSeconds = seconds % 60;
        int minutes = seconds/60;
        int hours = 0;
        if(minutes >= 60 ){
            hours = minutes / 60;
            minutes %= 60;
        }
        String remSeconds = remainderSeconds < 10 ? "0" + remainderSeconds : Integer.toString(remainderSeconds);
        String remMinutes = minutes < 10 ? "0" + minutes : Integer.toString(minutes);
        String remHours = hours < 10 ? "0" + hours : Integer.toString(hours);
        return remHours + ":" + remMinutes + ":" + remSeconds;
    }

    public Timer getTimer() {
        return timer;
    }
}