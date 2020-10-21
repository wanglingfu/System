import lombok.SneakyThrows;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @description: 测试多线程
 * @author: whj
 * @create: 2020-10-19 20:38
 **/
public class testThread extends Thread{
    public class test{
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(new Timestamp(System.currentTimeMillis())+"  "+i);
        }
    }
}
class testThread2 extends Thread{
    private test test;
    @Override
    public void run() {
        synchronized (test) {
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public synchronized void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(new Timestamp(System.currentTimeMillis()) + " hello world");
                }
            }, 0, 100);
        }
    }
}
