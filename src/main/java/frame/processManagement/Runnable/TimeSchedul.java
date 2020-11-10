package frame.processManagement.Runnable;

import Main.main;
import frame.processManagement.ProcessScheduling;
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
    private CPU cpu;
    private ProcessScheduling processScheduling;

    public TimeSchedul(CPU cpu,ProcessScheduling processScheduling) {
        this.cpu = cpu;
        this.processScheduling = processScheduling;
    }

    @Override
    public void run() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    main.lockTime.lock();
                    main.SystemTime++;
                    if(processScheduling.getRunPCB().getUuid() != processScheduling.getIdlePCB().getUuid()){
                        main.TimeSlice--;
                    }
                    if(main.TimeSlice == 0){
                        main.TimeSlice = 6;
                        cpu.setPSW(2);
                    }
                    if (main.DeviceTime != null) {
                        for (int i = 0; i < main.DeviceTime.length; i++) {
                            main.DeviceTime[i]--;
                            if(main.DeviceTime[i]==0){
                                cpu.setPSW(3);
                            }
                        }
                    }
                } finally {
                    main.lockTime.unlock();
                }
            }
        }, 0, 2000);
    }
}
