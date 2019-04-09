package com.xlauncher.es;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTaskTest {
    private static int rePushCount = 3;

    public static void main(String[] args) {
        timer();
    }

    public static void timer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (rePushCount > 0) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    rePushCount--;
                } else {
                    rePushCount = 3;
                    timer.cancel();
                }

            }
        }, 1000, 5000);
    }
}
