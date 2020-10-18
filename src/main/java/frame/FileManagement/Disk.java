package frame.FileManagement;

import java.io.*;

public class Disk {
    private byte[][] disk;
    public Disk() throws IOException {
        disk = new byte[256][64];
        disk[0][0] = (byte) 255;
        System.out.println(disk.getClass().getName());
        /*for(byte b:disk[0]){
            System.out.println(byteToUnigned(b));
        }*/
       /* FileOutputStream DISK_FILE = new FileOutputStream("disk.sim");
        ObjectOutputStream OOS = new ObjectOutputStream(DISK_FILE);
        try{
            OOS.writeObject(disk);
        } catch (IOException ex){
            throw ex;
        }*/
    };

    private int byteToUnigned(byte BYTE){
        int UNSIGNED_NUM = BYTE;
        UNSIGNED_NUM &= 0XFF;
        return UNSIGNED_NUM;
    }

    private void writeDisk(){

    };

    private void readDisk(){

    };
}
