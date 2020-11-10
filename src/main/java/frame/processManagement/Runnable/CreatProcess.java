package frame.processManagement.Runnable;

import Main.main;
import frame.processManagement.ProcessScheduling;
import frame.storageManagement.Memory;
import lombok.SneakyThrows;

import java.util.Random;

/**
 * @description: 创建新进程
 * @author: whj
 * @create: 2020-10-21 21:52
 **/
public class CreatProcess implements Runnable{
    private Byte[][] file;//全部文件
    private int flag;//是否执行完所有文件
    private ProcessScheduling processScheduling;//进程调度

    public CreatProcess(Byte[][] file,ProcessScheduling processScheduling) {
        this.file = file;
        flag = file.length;
        this.processScheduling = processScheduling;
    }

    public Byte[] getFile(int i) {
        return file[i];
    }

    public void setFile(Byte[][] file) {
        this.file = file;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    @SneakyThrows
    @Override
    public void run() {
        while(flag>0){
            main.lockCreate.lock();
            while(processScheduling.getProcessNum()>=10){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            main.lockCreate.unlock();
            Random random = new Random();
            int i = random.nextInt(file.length);
            while(file[i] == null){
                i = random.nextInt(file.length);
            }
            boolean b = processScheduling.create(file[i]);
            if(b){
                file[i] = null;
                flag--;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
