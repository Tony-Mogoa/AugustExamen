package august.examen.utils;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
    static Timer timer;
    static int interval;
    public static void main(String[] args) {
        interval = 60;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println(interval--);
                if (interval < 0)
                    timer.cancel();
            }
        }, 1000, 1000);


    }
}