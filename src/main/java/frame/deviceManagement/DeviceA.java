package frame.deviceManagement;

import java.util.LinkedList;

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
    public void getDevice(String Uid, int time, int size ){
         deviceWaitQueue =new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getA1() == null){
            deviceTable.setA1(Uid);
        }
        else if(deviceTable.getA2() == null){
            deviceTable.setA2(Uid);

        }
        else {
            block.add(deviceWaitQueue);
        }

    }

    public void removeDevice(String Uid){
        if (Uid.equals(deviceTable.getA1())){
            deviceTable.setA1(null);
        }else if (Uid.equals(deviceTable.getA2())){
            deviceTable.setA2(null);

        }
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
