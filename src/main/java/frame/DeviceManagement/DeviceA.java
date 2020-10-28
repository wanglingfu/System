package frame.DeviceManagement;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @ClassName Device
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/19 0:05
 * @Version 1.0
 **/
public class DeviceA {
      private LinkedList<DeviceWaitQueue> block ;
      private DeviceTable deviceTable;
      DeviceWaitQueue deviceWaitQueue;

    public DeviceA() {
        this.block = new LinkedList<DeviceWaitQueue>();
        this.deviceTable = new DeviceTable();
    }
    public DeviceA getDevice(String Uid, int time, int size ){
        DeviceWaitQueue deviceWaitQueue =new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getA1() == null){
            deviceTable.setA1(Uid);
            return this;
        }
        else if(deviceTable.getA2() == null){
            deviceTable.setA2(Uid);
            return this;
        }
        else {
            block.add(deviceWaitQueue);
            return this;
        }

    }

    public DeviceA removeDevice(String Uid){
        if (Uid.equals(deviceTable.getA1())){
            deviceTable.setA1(null);
            return this;
        }else if (Uid.equals(deviceTable.getA2())){
            deviceTable.setA2(null);
            return this;
        }
        else
        return this;
    }

    public DeviceA gerFirstNode(){
        deviceWaitQueue = block.removeFirst();
        getDevice(deviceWaitQueue.getUid(),deviceWaitQueue.getTime(),deviceWaitQueue.getSize());
        return this;
    }


    public DeviceTable getDeviceTable() {
        return deviceTable;
    }

    public void setDeviceTable(DeviceTable deviceTable) {
        this.deviceTable = deviceTable;
    }

    public LinkedList<DeviceWaitQueue> getBlock() {
        return block;
    }

    public void setBlock(LinkedList<DeviceWaitQueue> block) {
        this.block = block;
    }


}
