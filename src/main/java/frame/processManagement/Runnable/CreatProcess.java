package frame.processManagement.Runnable;

import frame.processManagement.ProcessScheduling;

/**
 * @description: 创建新进程
 * @author: whj
 * @create: 2020-10-21 21:52
 **/
public class CreatProcess implements Runnable{
    @Override
    public void run() {
        try {
            CPU.lockCreate.lock();
            ProcessScheduling processScheduling = new ProcessScheduling();
            processScheduling.create();
        } finally {
            CPU.lockCreate.unlock();
        }
    }
}
