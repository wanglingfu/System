package frame.processManagement;

/**
 * @description: 进程调度
 * @author: whj
 * @create: 2020-10-16 22:51
 **/
public class ProcessScheduling {
    public static PCB[] readyPCB;//就绪PCB队列
    public static PCB runPCB;//运行中的进程
    public static PCB[] blockPCB;//阻塞PCB队列

    /**
     * 创建进程
     */
    public synchronized void create(){

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
    public synchronized void awake(){

    }
}
