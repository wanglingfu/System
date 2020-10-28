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
            Byte[] bytes = new Byte[10];
            ProcessScheduling processScheduling = new ProcessScheduling();
            processScheduling.create(bytes);
        } finally {
            CPU.lockCreate.unlock();
        }
    }
}
