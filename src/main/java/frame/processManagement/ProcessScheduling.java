package frame.processManagement;

import frame.deviceManagement.DeviceA;
import frame.deviceManagement.DeviceBorC;
import frame.processManagement.Runnable.CPU;
import frame.storageManagement.Memory;

import java.util.Queue;

/**
 * @description: 进程调度
 * @author: whj
 * @create: 2020-10-16 22:51
 **/
public class ProcessScheduling {
    private Integer ProcessNum = 0;//现有进程数
    private Queue<PCB> readyPCB;//就绪PCB队列
    private Queue<PCB> blockPCB;//阻塞PCB队列
    private PCB runPCB;//运行中的进程
    private PCB idlePCB = new PCB(null);//闲置进程
    private Memory memory;
    private DeviceA deviceA;
    private DeviceBorC deviceB;
    private DeviceBorC deviceC;

    public ProcessScheduling(Memory memory, DeviceA deviceA, DeviceBorC deviceB, DeviceBorC deviceC) {
        this.memory = memory;
        this.deviceA = deviceA;
        this.deviceB = deviceB;
        this.deviceC = deviceC;
    }

    public Integer getProcessNum() {
        return ProcessNum;
    }

    public void setProcessNum(Integer processNum) {
        ProcessNum = processNum;
    }

    public Queue<PCB> getReadyPCB() {
        return readyPCB;
    }

    public void setReadyPCB(Queue<PCB> readyPCB) {
        this.readyPCB = readyPCB;
    }

    public Queue<PCB> getBlockPCB() {
        return blockPCB;
    }

    public void setBlockPCB(Queue<PCB> blockPCB) {
        this.blockPCB = blockPCB;
    }

    public PCB getRunPCB() {
        return runPCB;
    }

    public void setRunPCB(PCB runPCB) {
        this.runPCB = runPCB;
    }

    public PCB getIdlePCB() {
        return idlePCB;
    }

    public void setIdlePCB(PCB idlePCB) {
        this.idlePCB = idlePCB;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public DeviceA getDeviceA() {
        return deviceA;
    }

    public void setDeviceA(DeviceA deviceA) {
        this.deviceA = deviceA;
    }

    public DeviceBorC getDeviceB() {
        return deviceB;
    }

    public void setDeviceB(DeviceBorC deviceB) {
        this.deviceB = deviceB;
    }

    public DeviceBorC getDeviceC() {
        return deviceC;
    }

    public void setDeviceC(DeviceBorC deviceC) {
        this.deviceC = deviceC;
    }

    /**
     * 创建进程
     */
    public boolean create(Byte[] file){
        PCB pcb = new PCB(file);
        boolean b = memory.BestFit(memory, file.length, pcb.getUuid());
        ProcessNum++;
        readyPCB.add(pcb);
        return b;
    }

    /**
     * 销毁进程
     */
    public void destroy(PCB pcb){
        memory.releaseMemory(pcb.getUuid());
        ProcessNum--;
    }

    /**
     * 阻塞进程(还没写完)
     */
    public void block(String reason){
        if(reason == "A"){
            deviceA.getDevice(runPCB.getUuid(),runPCB.getTime(),runPCB.getFile().length);
        }else if(reason == "B"){
            deviceB.getDevice(runPCB.getUuid(),runPCB.getTime(),runPCB.getFile().length);
        }else if(reason == "C"){
            deviceC.getDevice(runPCB.getUuid(),runPCB.getTime(),runPCB.getFile().length);
        }
    }

    /**
     * 唤醒进程
     */
    public void awake(PCB pcb,String reason){
        if(reason == "A"){
            deviceA.removeDevice(runPCB.getUuid());
        }else if(reason == "B"){
            deviceB.removeDevice(runPCB.getUuid());
        }else if(reason == "C"){
            deviceC.removeDevice(runPCB.getUuid());
        }
    }
}
