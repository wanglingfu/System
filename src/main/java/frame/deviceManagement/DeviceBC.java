package frame.deviceManagement;

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

    public void getDevice(String Uid, int time, int size ){
        deviceWaitQueue =new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getB1() != null){
            deviceTable.setB1(Uid);
        }
        else if(deviceTable.getB2() != null){
            deviceTable.setB2(Uid);
        }
        else if(deviceTable.getB3() != null){
            deviceTable.setB3(Uid);
        }
        else{
            block.add(deviceWaitQueue);
        }
    }

    public void removeDevice(String Uid){
        if (Uid.equals(deviceTable.getB1())){
            deviceTable.setB1(null);
        }else if (Uid.equals(deviceTable.getB2())){ deviceTable.setB2(null);

        }
        else if (Uid.equals(deviceTable.getB3())){
            deviceTable.setB3(null);
        }

    }

    public void gerFirstNode(){
        deviceWaitQueue = block.removeFirst();
        getDevice(deviceWaitQueue.getUid(),deviceWaitQueue.getTime(),deviceWaitQueue.getSize());
    }

    public LinkedList<DeviceWaitQueue> getBlock() {
        return block;
    }

    public void setBlock(LinkedList<DeviceWaitQueue> block) {
        this.block = block;
    }

    public deviceTableBC getDeviceTable() {
        return deviceTable;
    }

    public void setDeviceTable(deviceTableBC deviceTable) {
        this.deviceTable = deviceTable;
    }

    public DeviceWaitQueue getDeviceWaitQueue() {
        return deviceWaitQueue;
    }

    public void setDeviceWaitQueue(DeviceWaitQueue deviceWaitQueue) {
        this.deviceWaitQueue = deviceWaitQueue;
    }
}
