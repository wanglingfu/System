package frame.processManagement.Runnable;

import Main.main;
import frame.processManagement.ProcessScheduling;

import java.util.Random;

/**
 * @description: 创建新进程
 * @author: whj
 * @create: 2020-10-21 21:52
 **/
public class CreatProcess implements Runnable{
    private Byte[][] file;
    private int flag;

    public CreatProcess(Byte[][] file) {
        this.file = file;
        flag = file.length;
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

    @Override
    public void run() {
        while(flag>0){
            while(ProcessScheduling.readyPCB.size()+ProcessScheduling.blockPCB.size()+(ProcessScheduling.runPCB==null?0:1)>=10){
            }
            try {
                main.lockCreate.lock();
                Random random = new Random();
                int i = random.nextInt(file.length);
                while(file[i] == null){
                    i = random.nextInt(file.length);
                }
                boolean b = ProcessScheduling.create(file[i]);
                if(b){
                    file[i] = null;
                    flag--;
                }
            } finally {
                main.lockCreate.unlock();
            }
        }
    }
}
