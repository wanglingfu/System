package frame.processManagement.Runnable;

import frame.processManagement.Runnable.CPU;

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
        try {
            CPU.lockTime.lock();
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    CPU.SystemTime++;
                    CPU.TimeSlice--;
                    for (int i = 0; i < CPU.DeviceTime.length; i++) {
                        CPU.DeviceTime[i]--;
                    }
                }
            }, 0, 1000);
        } finally {
            CPU.lockTime.unlock();
        }
    }
}
