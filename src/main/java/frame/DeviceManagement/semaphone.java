package frame.DeviceManagement;

import java.rmi.server.UID;
import java.util.Queue;

/**
 * @ClassName semaphone
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/19 20:26
 * @Version 1.0
 **/
public class semaphone {
    private  int count ;
    Queue <String>queue;

    public semaphone(int count){
        this.count = count;
    }

    /**
     *
     * @param s
     * @param Uid
     */
     public void semWait(semaphone s,String Uid){
        s.count--;
        if(s.count < 0){
            queue.add(Uid);
        }
     }
     public  void semSingal(semaphone s){
         s.count++;
         String st;

       //  return  st;
     }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
