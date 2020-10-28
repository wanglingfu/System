package frame.processManagement.Runnable;

import Main.main;
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
            main.lockCreate.lock();
            Byte[] bytes = new Byte[10];
            ProcessScheduling.create(bytes);
        } finally {
            main.lockCreate.unlock();
        }
    }
}
