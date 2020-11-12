package frame.processManagement;

import Main.main;
import frame.deviceManagement.Device;
import frame.storageManagement.Memory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @description: 进程调度
 * @author: whj
 * @create: 2020-10-16 22:51
 **/
public class ProcessScheduling {
    private Integer ProcessNum = 0;//现有进程数，只允许最多10个
    private Queue<PCB> readyPCB;//就绪PCB队列
    private ArrayList<PCB> blockPCB;//阻塞PCB队列
    private PCB idlePCB = new PCB(null);//闲置进程
    private PCB runPCB = idlePCB;//运行中的进程
    private Memory memory;
    private Device device;

    public ProcessScheduling(Memory memory, Device device) {
        this.memory = memory;
        this.device = device;
        readyPCB = new LinkedList<PCB>();
        blockPCB = new ArrayList<PCB>();
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

    public ArrayList<PCB> getBlockPCB() {
        return blockPCB;
    }

    public void setBlockPCB(ArrayList<PCB> blockPCB) {
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * 创建进程
     */
    public boolean create(byte[] file){
        main.lockCreate.lock();
        if(ProcessNum < 10){
            PCB pcb = new PCB(file);
            int size = 0;
            for (Byte aByte : file) {
                if(aByte == null){
                    break;
                }
                size++;
            }
            System.out.println(size);
            boolean b = memory.BestFit(size, pcb.getUuid());
            if(b){
                ProcessNum++;
                if(runPCB.getUuid() == idlePCB.getUuid()){
                    runPCB = pcb;
                }
                else{
                    readyPCB.add(pcb);
                }
            }
            main.lockCreate.unlock();
            return b;
        }
        main.lockCreate.unlock();
        return false;
    }

    /**
     * 销毁进程
     */
    public void destroy(){
        memory.releaseMemory(runPCB.getUuid());
        util(0);
        ProcessNum--;
    }

    /**
     * 阻塞进程
     */
    public void block(String reason){
        int deviceTime = 9;
        if(reason == "A"){
            deviceTime = device.getDeviceA(runPCB.getUuid(),runPCB.getTime(),runPCB.getFile().length);
        }else if(reason == "B"){
            deviceTime = device.getDeviceB(runPCB.getUuid(),runPCB.getTime(),runPCB.getFile().length);
        }else if(reason == "C"){
            deviceTime = device.getDeviceC(runPCB.getUuid(),runPCB.getTime(),runPCB.getFile().length);
        }
        if(deviceTime <9){
            main.DeviceTime[deviceTime-1] = runPCB.getTime();
        }
        util(1);
    }

    /**
     * 唤醒进程
     */
    public void awake(PCB pcb){
        String reason = pcb.getReason();
        int[] ints = new int[0];
        if(reason == "A"){
            ints = device.removeDeviceA(pcb.getUuid());
        }else if(reason == "B"){
            ints = device.removeDeviceB(pcb.getUuid());
        }else if(reason == "C"){
            ints = device.removeDeviceC(pcb.getUuid());
        }
        if(ints != null)
        main.DeviceTime[ints[0]-1] = ints[1];
        /**
         * 是否为闲置进程
         */
        if(runPCB.getUuid() != idlePCB.getUuid()){
            readyPCB.add(pcb);
        }else{
            runPCB = pcb;
        }
    }

    /**
     * 工具类 替换现有进程
     */
    public void util(int select){
        /**
         * 阻塞
         */
        if(select == 1){
            blockPCB.add(runPCB);
        }
        /**
         * 时间片截止
         */
        else if(select == 2){
            readyPCB.add(runPCB);
        }
        if(readyPCB.size()>0){
            runPCB = readyPCB.remove();
        }
        else{
            runPCB = idlePCB;
        }
        main.TimeSlice = 6;
    }

}
