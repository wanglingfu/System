package frame.deviceManagement;

/**
 * @ClassName DeviceWaitQueue
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/20 18:24
 * @Version 1.0
 **/
public class DeviceWaitQueue {
    private String uid;
    private int size;
    private int time;

    public  DeviceWaitQueue(String Uid, int size, int  time ){
        this.uid = Uid;
        this.size = size;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
