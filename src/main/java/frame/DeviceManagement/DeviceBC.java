package frame.DeviceManagement;

import java.util.LinkedList;

/**
 * @ClassName DeviceBC
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/27 20:07
 * @Version 1.0
 **/
public class DeviceBC {
    private LinkedList<DeviceWaitQueue> block ;
    private deviceTableBC deviceTable;
    DeviceWaitQueue deviceWaitQueue;

    public DeviceBC() {
        this.block = new LinkedList<DeviceWaitQueue>();
        this.deviceTable = new deviceTableBC();
    }

    public DeviceBC getDevice(String Uid, int time, int size ){
        DeviceWaitQueue deviceWaitQueue =new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getB1() != null){
            deviceTable.setB1(Uid);
            return this;
        }
        else if(deviceTable.getB2() != null){
            deviceTable.setB2(Uid);
            return this;
        }
        else if(deviceTable.getB3() != null){
            deviceTable.setB3(Uid);
            return this;
        }
        else{
            block.add(deviceWaitQueue);
            return this;
        }
    }

    public DeviceBC removeDevice(String Uid){
        if (Uid.equals(deviceTable.getB1())){
            deviceTable.setB1(null);
            return this;
        }else if (Uid.equals(deviceTable.getB2())){
            deviceTable.setB2(null);
            return this;
        }
        else if (Uid.equals(deviceTable.getB3())){
            deviceTable.setB3(null);
            return this;
        }
        else
            return this;
    }

    public DeviceBC gerFirstNode(){
        deviceWaitQueue = block.removeFirst();
        getDevice(deviceWaitQueue.getUid(),deviceWaitQueue.getTime(),deviceWaitQueue.getSize());
        return this;
    }
}
