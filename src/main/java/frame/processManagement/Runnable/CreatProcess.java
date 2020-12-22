package frame.processManagement.Runnable;

import frame.FileManagement.FileUtil;
import frame.processManagement.ProcessScheduling;
import frame.processManagement.Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * @description: 创建新进程
 * @author: whj
 * @create: 2020-10-21 21:52
 **/
public class CreatProcess{
    /**
     * 全部文件
     */
    private ArrayList<String> file;
    /**
     * 是否执行完所有文件
     */
    private int flag;
    /**
     * 进程调度
     */
    private ProcessScheduling processScheduling;
    /**
     * 文件工具
     */
    private FileUtil fileUtil;

    public CreatProcess(ArrayList<String> file,ProcessScheduling processScheduling,FileUtil fileUtil) {
        this.file = file;
        flag = file.size();
        this.processScheduling = processScheduling;
        this.fileUtil = fileUtil;
    }

    public ArrayList<String> getFile() {
        return file;
    }

    public void setFile(ArrayList<String> file) {
        this.file = file;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public void run() {
        while(flag>0){
            while(processScheduling.getProcessNum()>=10){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Random random = new Random();
            int i = random.nextInt(file.size()) ;
            while(file.get(i) == null){
                i = random.nextInt(file.size()) ;
            }
            String fileContent = fileUtil.getFileContent(file.get(i));
            byte[] byteFile = Util.getByteFile(fileContent);
            boolean b = processScheduling.create(byteFile);
            if(b){
                file.remove(i);
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
