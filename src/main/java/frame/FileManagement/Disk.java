package frame.FileManagement;

import java.io.*;
/**
 * @author ：Vizzk
 * @description：磁盘管理
 */
public class Disk {
    private byte[][] disk;
    public Disk() throws Exception {
        readDisk();
        printDisk();
        disk[0][1] = (byte)234;
        writeDisk();
        printDisk();
    };

    /**
     * @author: Vizzk
     * @description: 读取的符号字节 转换为 无符号
     * @param BYTE
     * @return int
     */
    private int byteToUnigned(byte BYTE){
        int UNSIGNED_NUM = BYTE;
        UNSIGNED_NUM &= 0XFF;
        return UNSIGNED_NUM;
    }

    public void writeDisk() throws Exception{
        FileOutputStream DISK_FILE = new FileOutputStream("disk.sim");
        ObjectOutputStream OOS = new ObjectOutputStream(DISK_FILE);
        OOS.writeObject(this.disk);
        OOS.close();
    };

    /**
     * @author: Vizzk
     * @description: 读取磁盘所有信息
     * @param
     * @return void
     */
    public void readDisk()throws Exception{
        FileInputStream DISK_FILE = new FileInputStream("disk.sim");
        ObjectInputStream OIS = new ObjectInputStream(DISK_FILE);
        this.disk = (byte[][]) OIS.readObject();
        OIS.close();
    };

    public void printDisk(){
        for(byte i:disk[0]){
            System.out.println(byteToUnigned(i));
        }
    }
}
