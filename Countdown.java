package august.examen.controllers;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    public static void main(final String args[]) {
        if (args.length != 1) {
            System.err.println("Input second => : ");
            System.exit(1);
        }
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int interval = Integer.parseInt(args[0]);
            public void run() {
                System.out.println(interval--);
                if (interval< 0)
                    timer.cancel();
            }
        }, 1000, 1000);
    }
}