import frame.FileManagement.Disk;
import frame.FileManagement.FileUtil;
import org.junit.Test;

import java.lang.System;
import java.util.Arrays;

/**
 * @author ：Vizzk
 * @description：文件测试类
 * @date ：2020/10/18 10:18
 */
public class TestFile {
    @Test
    public void testDisk() throws Exception{

        FileUtil fileUtil= new FileUtil();
        String S = "/ad/sdf/cc.e";
        System.out.println((byte)150);
        //fileUtil.makeDirectory(S);
        /*byte[][] bytes = fileUtil.formatPath(S);
        for(byte[] b:bytes){
            System.out.println(Arrays.toString(b));
            System.out.println(fileUtil.bytesToString(b));
        }*/
    }
    @Test
    public void testFormate() throws Exception{
        Disk disk = new Disk();
        String S = "/abc";
        byte[][] bytes = disk.formatPath(S);
        for(byte[] b:bytes) {
            System.out.println(Arrays.toString(b));
        }
    }
    @Test
    public void printDisk() throws Exception{
        Disk disk = new Disk();
        disk.printDisk();
    }
    @Test
    public void reWrite() throws Exception{
        Disk disk = new Disk();
    }
    @Test
    public void makeDir() throws Exception{
        FileUtil fileUtil = new FileUtil();
        fileUtil.makeDirectory("/abg/def/hjk");
        /*for(String s:bytePath){，
            bytes = stringToBytes(s);
            System.out.println(Arrays.toString(bytes));
            bytesToString(bytes);
        }*/
    }
    @Test
    public void findDir() throws Exception{
        FileUtil fileUtil = new FileUtil();
        Disk disk = new Disk();
        String S = "/abc";
        byte[][] bytes = disk.formatPath(S);
        byte[][] bytes1 = Arrays.copyOf(bytes,bytes.length-1);
        int dirblock = fileUtil.findDirectory(bytes);
        System.out.println(dirblock);

    }

    @Test
    public void getBlock() throws Exception{
        FileUtil fileUtil = new FileUtil();
        Disk disk = new Disk();
        String S = "/abg";
        byte[][] bytes = disk.formatPath(S);
        int dir = fileUtil.getBlock(bytes[0], (byte)3, 4);
        System.out.println(dir);
    }
}
