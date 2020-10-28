package frame.processManagement;

import frame.processManagement.Runnable.CPU;

/**
 * @description: 进程调度
 * @author: whj
 * @create: 2020-10-16 22:51
 **/
public class ProcessScheduling {


    /**
     * 创建进程
     */
    public synchronized void create(Byte[] file){
        PCB pcb = new PCB(file);
        CPU.readyPCB.add(pcb);
    }

    /**
     * 销毁进程
     */
    public synchronized void destroy(){

    }

    /**
     * 阻塞进程
     */
    public synchronized void block(){

    }

    /**
     * 唤醒进程
     */
    public synchronized void awake(PCB pcb){

    }
}
