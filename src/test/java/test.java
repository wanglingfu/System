//
//import Main.main;
//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
//import frame.deviceManagement.Device;
//import frame.processManagement.PCB;
//import frame.processManagement.ProcessScheduling;
//import frame.processManagement.Runnable.CPU;
//import frame.processManagement.Runnable.CreatProcess;
//import frame.processManagement.Util;
//import frame.storageManagement.Memory;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * @description: 测试类
// * @author: whj
// * @create: 2020-10-13 23:04
// **/
//public class test {
//    /**
//     * 编码规则：
//     * x++:00000000
//     * x—:00100000
//     * !A?:01000xxx
//     * !B?:01001xxx
//     * !C?:01010xxx
//     * end:01100000
//     * x=?:1xxxxxxx
//     * 特殊：
//     * !A8:00001000
//     * !A9:00001001
//     * !B8:00011000
//     * !B9:00011001
//     * !C8:00101000
//     * !C9:00101001
//     */
//    @Test
//    public void Test(){
//        String IR = null;
//        int i = Util.compile("x=0");
//        int code = i/64;//是否为特殊
//        int[] device = {(i % 64) / 16, (i % 64) / 8};
//        int[] time = {i%16,i%8};
//        //x++指令
//        if (i==0){
//            IR = "x++";
//        }
//        //x--指令
//        else if (i==32){
//            IR = "x--";
//        }
//        //end指令
//        else if (i == 96){
//            IR = "end";
//        }
//
//        //x=?指令
//        else if (i<0){
//            i = i + 128;
//            IR = "x="+i;
//        }
//        //！？？指令
//        else {
//            if(device[code] == 0){
//                IR = "!A"+time[code];
//            }
//            if(device[code] == 1){
//                IR = "!B"+time[code];
//            }
//            if(device[code] == 2){
//                IR = "!C"+time[code];
//            }
//        }
//        System.out.println(IR);
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        File test = new File("D:\\yiban\\System\\src\\test\\java\\test");
//        Reader reader = new FileReader(test);
//        char[] s = new char[10000];
//        reader.read(s);
//        String s1 = String.valueOf(s);
//        String[] split = s1.split("\r\n");
//        byte[][] files = new byte[split.length][1000];
//        for (int i = 0; i < split.length; i++) {
//            String[] s2 = split[i].split(" ");
//            for (int j = 0; j < s2.length; j++) {
//                files[i][j] = Util.compile(s2[j]);
//            }
//        }
//        Memory memory = new Memory(150);
//        Device device = new Device();
//        ProcessScheduling processScheduling = new ProcessScheduling(memory,device);
//        CPU cpu = new CPU(files.length, processScheduling,processScheduling.getIdlePCB().getUuid());
//        CreatProcess creatProcess = new CreatProcess(files,processScheduling);
//        thread.start();
//
//
//    }
//    @Test
//    public void test3() throws IOException {
//        File test = new File("test");
//        Reader reader = new FileReader(test);
//        char[] s = new char[10000];
//        reader.read(s);
//        String s1 = String.valueOf(s);
//        String[] split = s1.split("\r\n");
//        System.out.println(split.length);
//        byte[][] files = new byte[split.length-1][1000];
//        for (int i = 0; i < split.length-1; i++) {
//            String[] s2 = split[i].split(" ");
//            for (int j = 0; j < s2.length; j++) {
//                files[i][j] = Util.compile(s2[j]);
//            }
//        }
//        for (int i = 0; i < files.length; i++) {
//            for (int i1 = 0; i1 < files[i].length; i1++) {
//                System.out.println(files[i][i1]);
//            }
//        }
//    }
//    @Test
//    public void test4(){
//        Queue<PCB> pcbs = new LinkedList<PCB>();
//        pcbs.add(new PCB(null));
//        pcbs.add(new PCB(null));
//        System.out.println(pcbs.size());
//        for (int i = 0; i < pcbs.size();) {
//            PCB p = pcbs.remove();
//        }
//        System.out.println(pcbs.size());
//    }
//    @Test
//    public void test5() throws IOException {
//        File test = new File("D:\\yiban\\System\\src\\test\\java\\test2");
//        Reader reader = new FileReader(test);
//        char[] s = new char[10000];
//        reader.read(s);
//        String s1 = String.valueOf(s);
//        byte[] byteFile = Util.getByteFile(s1);
//        String stringFile = Util.getStringFile(byteFile);
//        System.out.println(stringFile);
//    }
//}
