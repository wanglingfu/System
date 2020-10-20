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
    };

    /**
     * @author: Vizzk
     * @description: 读取的符号字节 转换为 无符号
     * @param BYTE
     * @return int
     */
    public static int byteToUnsigned(byte BYTE){
        int UNSIGNED_NUM = BYTE;
        UNSIGNED_NUM &= 0XFF;
        return UNSIGNED_NUM;
    }

    /**
     * @author: Vizzk
     * @description: 将磁盘模拟信息写入文件
     * @param
     * @return void
     */
    public void writeDisk() throws Exception{
        FileOutputStream disk = new FileOutputStream("disk.sim");
        ObjectOutputStream oos = new ObjectOutputStream(disk);
        oos.writeObject(this.disk);
        oos.close();
    };

    /**
     * @author: Vizzk
     * @description: 读取文件中的磁盘信息
     * @param
     * @return void
     */
    public void readDisk()throws Exception{
        FileInputStream disk = new FileInputStream("disk.sim");
        ObjectInputStream ois = new ObjectInputStream(disk);
        this.disk = (byte[][]) ois.readObject();
        ois.close();
    };

    public byte[][] getDisk() {
        return disk;
    }

    public void printDisk(){
        for(byte i:disk[0]){
            System.out.println(byteToUnsigned(i));
        }
    }
}
