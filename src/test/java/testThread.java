//import lombok.SneakyThrows;
//
//import java.sql.Timestamp;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * @description: 测试多线程
// * @author: whj
// * @create: 2020-10-19 20:38
// **/
//public class testThread extends Thread{
//    private boolean flag = true;
//
//    public void setFlag(boolean flag) {
//        this.flag = flag;
//    }
//
//    @Override
//    public void run() {
//        while(flag){
//            System.out.println(1);
//        }
//    }
//}
//class testThread2 extends Thread{
//    private test test;
//    @Override
//    public void run() {
//        synchronized (test) {
//            Timer t = new Timer();
//            t.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public synchronized void run() {
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(new Timestamp(System.currentTimeMillis()) + " hello world");
//                }
//            }, 0, 100);
//        }
//    }
//}
