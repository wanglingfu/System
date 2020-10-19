package frame.DeviceManagement;

import java.util.Queue;

/**
 * @ClassName Device
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/19 0:05
 * @Version 1.0
 **/
public class Device {
    private  int A ;
    Queue queue;

    public Device(int A){
        this.A = A;
    }

    /**
     *
     * @return
     */


    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
