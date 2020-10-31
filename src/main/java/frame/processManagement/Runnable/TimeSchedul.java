package frame.processManagement.Runnable;

import Main.main;
import frame.processManagement.Runnable.CPU;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @description: 系统时钟等定时任务
 * @author: whj
 * @create: 2020-10-21 21:24
 **/
public class TimeSchedul implements Runnable{
    @Override
    public void run() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    main.lockTime.lock();
                    main.SystemTime++;
                    main.TimeSlice--;
                    if (main.DeviceTime != null) {
                        for (int i = 0; i < main.DeviceTime.length; i++) {
                            main.DeviceTime[i]--;
                        }
                    }
                } finally {
                    main.lockTime.unlock();
                }
            }
        }, 0, 1000);
    }
}
