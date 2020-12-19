import frame.FileManagement.Disk;
import frame.FileManagement.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author ：Vizzk
 * @description：文件测试类
 * @date ：2020/10/18 10:18
 */
public class TestFile {
    @Test
    public void testFormate() throws Exception{
        Disk disk = new Disk();
        disk.formatPath("/abc/cde");
    }
    @Test
    public void printFile() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/bcd.exe";
        String content = fileUtil.getFileContent(path);
        System.out.println(content);
    }
    @Test
    public void copyFile() throws Exception {
        FileUtil fileUtil = new FileUtil();
        String srcPath = "/abc/def";
        String destPath = "/abc/abc";
        fileUtil.copyFile(srcPath,destPath);
    }
    @Test
    public void createfile() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/abc/def.txt";
        String content = "abcdefghijklnsssmaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        fileUtil.createFile(path, content);
    }
    @Test
    public void createExeFile() throws Exception{
        File test = new File("H:\\test.txt");
        Reader reader = new FileReader(test);
        char[] s = new char[10000];
        reader.read(s);
        String s1 = String.valueOf(s);
        System.out.println(s1);
        FileUtil fileUtil = new FileUtil();

        String path = "/bcd.exe";
        String content = s1;
        fileUtil.createFile(path, s1);
    }
    @Test
    public void delectfile() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/bcd.e";
        fileUtil.deleteFile(path);
    }
    @Test
    public void printDisk() throws Exception{
        Disk disk = new Disk();
        disk.printDisk();
    }
    @Test
    public void printDir() throws Exception{
        FileUtil fileUtil = new FileUtil();
        ArrayList<String> list = fileUtil.getDirectorys("root");
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
    @Test
    public void reWrite() throws Exception{
        Disk disk = new Disk();
    }
    @Test
    public void makeDir() throws Exception{
        FileUtil fileUtil = new FileUtil();
        fileUtil.makeDirectory("/abc/bcd");

    }
    @Test
    public void deleteAll() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/abc";
        fileUtil.deleteAll(path);

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
        String S = "/abc/bcd";
        byte[][] bytes = disk.formatPath(S);
        byte[][] bytes1 = Arrays.copyOf(bytes,bytes.length-1);
        int dirblock = fileUtil.findDirectory(bytes);
        System.out.println(dirblock);

    }

    @Test
    public void getBlock() throws Exception{
        FileUtil fileUtil = new FileUtil();
        Disk disk = new Disk();
        String S = "/abc";
        byte[][] bytes = disk.formatPath(S);
        int dir = fileUtil.getBlock(bytes[0], (byte)3, 4);
        System.out.println(dir);
    }
    @Test
    public void getExe() throws Exception{
        FileUtil fileUtil = new FileUtil();
        ArrayList<String> list = fileUtil.getExeFiles();
        for(String s:list){
            System.out.println(s);
        }
    }
    @Test
    public void getblocks() throws Exception{
        FileUtil fileUtil = new FileUtil();
        String path = "/abc/def.t";
        ArrayList<Integer> list = fileUtil.getFileBlock(path);
        for(int s:list){
            System.out.println(s);
        }
    }
    @Test
    public void getFat() throws Exception{
        FileUtil fileUtil = new FileUtil();
        int[] s = fileUtil.getFAT();
        for(int i:s){
            System.out.println(i);
        }
    }
}
