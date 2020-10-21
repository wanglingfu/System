package frame.DeviceManagement;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Device
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/19 0:05
 * @Version 1.0
 **/
public class Device {
      private   Queue <DeviceWaitQueue> queues;
      private Semaphore semaphore;
      private boolean resourceArray[];
      private final ReentrantLock lock;
        public Device(int deviceCount){
            this.resourceArray = new boolean[deviceCount];
            this.semaphore = new Semaphore(deviceCount,true);
            this.lock = new ReentrantLock(true);
            this.queues = new LinkedList<DeviceWaitQueue>();
            for(int i = 0; i < deviceCount; i++){
                resourceArray[i] = true;
            }
        }
        public void useDevice(int uid){
            try{
                semaphore.acquire();
                int id = getDevicId();
                System.out.print("userId:"+uid+"正在使用资源，资源id:"+id+"\n");
                Thread.sleep(100);
                resourceArray[id] = true;
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                semaphore.release();//释放信号量，计数器加1
            }
        }

    private int getDevicId() {
            int id = -1;
        try {
            lock.lock();
            for(int i=0; i<10; i++){
                if(resourceArray[i]){
                    resourceArray[i] = false;
                    id = i;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
            return id;
    }


}
