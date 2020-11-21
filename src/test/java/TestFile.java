import frame.FileManagement.Disk;
import frame.FileManagement.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.System;
import java.util.Arrays;

/**
 * @author ：Vizzk
 * @description：文件测试类
 * @date ：2020/10/18 10:18
 */
public class TestFile {
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
    public void createfile() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/def/def";
        String content = "abcdefghijklnsssm";
        fileUtil.createFile(path, content);
    }
    @Test
    public void createExeFile() throws Exception{
        File test = new File("H:\\test");
        Reader reader = new FileReader(test);
        char[] s = new char[10000];
        reader.read(s);
        String s1 = String.valueOf(s);
        System.out.println(s1);
        FileUtil fileUtil = new FileUtil();

        String path = "/def/abc.e";
        String content = s1;
        fileUtil.createFile(path, s1);
    }
    @Test
    public void delectfile() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/def/abc.e";
        fileUtil.deleteFile(path);
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
        fileUtil.makeDirectory("/def/abc");

    }
    @Test
    public void deleteDir() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/def/abc";
        fileUtil.removeDirectory(path);

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
