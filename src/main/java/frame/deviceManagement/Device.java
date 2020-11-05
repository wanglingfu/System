package frame.deviceManagement;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @ClassName Device
 * @Description 设备
 * @Author wlf
 * @Date 2020/10/19 0:05
 * @Version 1.0
 **/
public class Device {
      private LinkedList<DeviceWaitQueue> blockA ;
      private LinkedList<DeviceWaitQueue> blockB ;
      private LinkedList<DeviceWaitQueue> blockC ;
      private DeviceTable deviceTable;
      DeviceWaitQueue deviceWaitQueueA;
      DeviceWaitQueue deviceWaitQueueB;
      DeviceWaitQueue deviceWaitQueueC;
    public Device() {
        this.blockA = new LinkedList<DeviceWaitQueue>();
        this.blockB = new LinkedList<DeviceWaitQueue>();
        this.blockC = new LinkedList<DeviceWaitQueue>();
        this.deviceTable = new DeviceTable();
    }
    /**
     * @Description 申请A设备
     * @param Uid
     * @param time
     * @param size
     */
    public void getDeviceA(String Uid, int time, int size ){
         deviceWaitQueueA =new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getA1() == null){
            deviceTable.setA1(Uid);
        }
        else if(deviceTable.getA2() == null){
            deviceTable.setA2(Uid);
        }
        else {
            blockA.add(deviceWaitQueueA);
        }

    }
    /**
     * @Description 释放A设备
     * @param Uid
     */
    public int[] removeDeviceA(String Uid){
        int[] re =new int[2];
        if (Uid.equals(deviceTable.getA1())){
            deviceTable.setA1(null);
            if(!blockA.isEmpty()) {
                gerFirstNodeA();
                re[0] = 1;
                re[1] = deviceWaitQueueA.getTime();
                return re;
            }
            return null;
        }else if (Uid.equals(deviceTable.getA2())){
            deviceTable.setA2(null);
            if( !blockA.isEmpty()) {
                gerFirstNodeA();
                re[0] = 2;
                re[1] = deviceWaitQueueA.getTime();
                return re;
            }
            return null;
        }else
            return null;
    }

    /**
     * @Description 获取等待队列第一个节点
     */
    public void gerFirstNodeA(){
        deviceWaitQueueA = blockA.removeFirst();
        getDeviceA(deviceWaitQueueA.getUid(),deviceWaitQueueA.getTime(),deviceWaitQueueA.getSize());
    }
    /**
     * @Description 申请B设备
     * @param Uid
     * @param time
     * @param size
     */
    public void getDeviceB(String Uid, int time, int size ){
        deviceWaitQueueB =new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getB1() == null){
            deviceTable.setB1(Uid);
        }
        else if(deviceTable.getB2() == null){
            deviceTable.setB2(Uid);
        }
        else if(deviceTable.getB3() == null){
            deviceTable.setB3(Uid);
        }
        else{
            blockB.add(deviceWaitQueueB);
        }
    }
    /**
     * @Description 释放B设备
     * @param Uid
     */
    public int[] removeDeviceB(String Uid){
        int[] re =new int[2];
        if (Uid.equals(deviceTable.getB1())){

            deviceTable.setB1(null);
            if(!blockB.isEmpty()) {
                gerFirstNodeB();
                re[0] = 3;
                re[1] = deviceWaitQueueB.getTime();
                return re;
            }
            return null;
        }else if (Uid.equals(deviceTable.getB2())){
            deviceTable.setB2(null);
            if(!blockB.isEmpty()) {
                gerFirstNodeB();
                re[0] = 4;
                re[1] = deviceWaitQueueB.getTime();
                return re;
            }
            return null;
        }
        else if (Uid.equals(deviceTable.getB3())){
            deviceTable.setB3(null);
            if(!blockB.isEmpty()) {
                gerFirstNodeB();
                re[0] = 5;
                re[1] = deviceWaitQueueB.getTime();
                return re;
            }
            return null;
        }else
            return null;
    }
    /**
     * @Description 获取B设备等待队列第一个节点
     */
    public void gerFirstNodeB(){
        deviceWaitQueueB = blockB.removeFirst();
        getDeviceB(deviceWaitQueueB.getUid(),deviceWaitQueueB.getTime(),deviceWaitQueueB.getSize());
    }
    /**
     * @Description 申请C设备
     * @param Uid
     * @param time
     * @param size
     */
    public void getDeviceC(String Uid, int time, int size ){
        deviceWaitQueueC = new DeviceWaitQueue(Uid,size,time);
        if (deviceTable.getC1() == null){
            deviceTable.setC1(Uid);
        }
        else if(deviceTable.getC2() == null){
            deviceTable.setC2(Uid);
        }
        else if(deviceTable.getC3() == null){
            deviceTable.setC3(Uid);
        }
        else{
            blockC.add(deviceWaitQueueC);
        }
    }
    /**
     * @Description 释放C设备
     * @param Uid
     */
    public int[] removeDeviceC(String Uid){
        int[] re = new int[2];
        if (Uid.equals(deviceTable.getB1())){
            deviceTable.setC1(null);
            if(!blockC.isEmpty()) {
                gerFirstNodeC();
                re[0] = 6;
                re[1] = deviceWaitQueueC.getTime();
                return re;
            }
            return null;
        }else if (Uid.equals(deviceTable.getB2())){
            deviceTable.setC2(null);
            if(!blockC.isEmpty()) {
                gerFirstNodeC();
                re[0] = 7;
                re[1] = deviceWaitQueueC.getTime();
                return re;
            }
            return null;
        }
        else if (Uid.equals(deviceTable.getB3())){
            deviceTable.setC3(null);
            if(!blockC.isEmpty()) {
                gerFirstNodeC();
                re[0] = 8;
                re[1] = deviceWaitQueueC.getTime();
                return re;
            }
            return null;
        }else
            return  null;
    }
    /**
     * @Description 获取B设备等待队列第一个节点
     */
    public void gerFirstNodeC(){
        deviceWaitQueueC = blockB.removeFirst();
        getDeviceC(deviceWaitQueueC.getUid(),deviceWaitQueueC.getTime(),deviceWaitQueueC.getSize());
    }

    public DeviceTable getDeviceTable() {
        return deviceTable;
    }

    public void setDeviceTable(DeviceTable deviceTable) {
        this.deviceTable = deviceTable;
    }

    public LinkedList<DeviceWaitQueue> getBlockA() {
        return blockA;
    }

    public void setBlockA(LinkedList<DeviceWaitQueue> blockA) {
        this.blockA = blockA;
    }

    public LinkedList<DeviceWaitQueue> getBlockB() {
        return blockB;
    }

    public void setBlockB(LinkedList<DeviceWaitQueue> blockB) {
        this.blockB = blockB;
    }

    public LinkedList<DeviceWaitQueue> getBlockC() {
        return blockC;
    }

    public void setBlockC(LinkedList<DeviceWaitQueue> blockC) {
        this.blockC = blockC;
    }

    public DeviceWaitQueue getDeviceWaitQueueA() {
        return deviceWaitQueueA;
    }

    public void setDeviceWaitQueueA(DeviceWaitQueue deviceWaitQueueA) {
        this.deviceWaitQueueA = deviceWaitQueueA;
    }

    public DeviceWaitQueue getDeviceWaitQueueB() {
        return deviceWaitQueueB;
    }

    public void setDeviceWaitQueueB(DeviceWaitQueue deviceWaitQueueB) {
        this.deviceWaitQueueB = deviceWaitQueueB;
    }

    public DeviceWaitQueue getDeviceWaitQueueC() {
        return deviceWaitQueueC;
    }

    public void setDeviceWaitQueueC(DeviceWaitQueue deviceWaitQueueC) {
        this.deviceWaitQueueC = deviceWaitQueueC;
    }
}
