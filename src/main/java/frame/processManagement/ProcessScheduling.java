package frame.processManagement;

import frame.processManagement.Runnable.CPU;

import java.util.Queue;

/**
 * @description: 进程调度
 * @author: whj
 * @create: 2020-10-16 22:51
 **/
public class ProcessScheduling {
    public static Integer ProcessNum = 0;//现有进程数
    public static Queue<PCB> readyPCB;//就绪PCB队列
    public static Queue<PCB> blockPCB;//阻塞PCB队列
    public static PCB runPCB;//运行中的进程
    /**
     * 创建进程
     */
    public static void create(Byte[] file){

    }

    /**
     * 销毁进程
     */
    public static void destroy(){

    }

    /**
     * 阻塞进程
     */
    public static void block(){

    }

    /**
     * 唤醒进程
     */
    public static void awake(PCB pcb){

    }
}
