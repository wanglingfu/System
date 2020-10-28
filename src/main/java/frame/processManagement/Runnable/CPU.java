package frame.processManagement.Runnable;

import frame.processManagement.PCB;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 执行进程
 * @author: whj
 * @create: 2020-10-19 22:30
 **/
public class CPU implements Runnable{
    public static Queue<PCB> readyPCB;//就绪PCB队列
    public static Queue<PCB> blockPCB;//阻塞PCB队列
    public static PCB runPCB;//运行中的进程
    public static int SystemTime=0;//系统时间
    public static int TimeSlice=6;//时间片
    public static int[] DeviceTime;//设备时间
    public static final ReentrantLock lockTime = new ReentrantLock();
    public static final ReentrantLock lockCreate = new ReentrantLock();
    @Override
    public void run() {

    }
}
