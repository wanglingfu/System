package frame.FileManagement;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author ：Vizzk
 * @description：磁盘管理
 */
public class Disk {
    private byte[][] disk;
    public Disk() throws Exception {
        /*disk = new byte[256][64];
        for(int i=0; i<5; i++){
            disk[0][i] = 1;
        }
        writeDisk();*/
        readDisk();
    };
    /**
     * @author: Vizzk
     * @description: 将number转换为2字节的整数,第0位是个位
     * @param number
     * @return byte[]
     */
    public static byte[] lengthToBytes(int number){
        byte[] bytes = new byte[2];
        int temp = number & 0x000000ff;
        bytes[0] = (byte)temp;
        temp = number >> 8;
        bytes[1] = (byte)temp;
        return bytes;
    }
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

    /**
     * @author: Vizzk
     * @description: 字符串转换为字节数组
     * @param string
     * @return byte[]
     */
    public byte[] stringToBytes(String string){
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        return bytes;
    }

    /**
     * @author: Vizzk
     * @description: 字节数组转换为字符串
     * @param bytes
     * @return java.lang.String
     */
    public String bytesToString(byte[] bytes){
        String string = new String(bytes, Charset.forName("UTF-8"));
        return string;
    }

    /**
     * @author: Vizzk
     * @description: 将路径字符串格式化为字节型并返回一个二维字节数组,数组每行为文件或目录名字数据
     * @param path
     * @return byte[][]
     */
    public byte[][] formatPath(String path){
        String temp= path.split("\\.")[0];
        String[] directory;
        byte[][] bytePath;
        directory = temp.split("/");
        if(directory[0].equals("")){
            directory = Arrays.copyOfRange(directory,1,directory.length);
        }
        bytePath = new byte[directory.length][3];
        for(int i=0; i<directory.length; i++){
            byte[] bytes = this.stringToBytes(directory[i]);
            int destPos = 0;
            if(bytes.length!=3){
                destPos=3-bytes.length;
            }
            System.arraycopy(bytes,0, bytePath[i], destPos,bytes.length);
        }
        return bytePath;
    }

    public void printDisk(){
        for(int i=0; i<256; i++){
            for(byte b:disk[i]){
                System.out.print(byteToUnsigned(b) + " ");
            }
            System.out.println();
        }
    }

}
