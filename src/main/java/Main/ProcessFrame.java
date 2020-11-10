package Main;

import frame.deviceManagement.Device;
import frame.processManagement.PCB;
import frame.processManagement.ProcessScheduling;
import frame.processManagement.Runnable.CPU;
import frame.processManagement.Runnable.CreatProcess;
import frame.processManagement.Runnable.TimeSchedul;
import frame.processManagement.Util;
import frame.storageManagement.Hole;
import frame.storageManagement.Memory;
import frame.storageManagement.Sleep;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int dp_method=0;
    JLabel jLabel1=new JLabel();  //系统时钟
    JLabel jLabel2=new JLabel();  //正在运行进程id
    JLabel jLabel3=new JLabel();  //时间片
    JLabel jLabel4=new JLabel();  //用户接口命令
    JLabel jLabel5=new JLabel();  //就 绪 队 列 进 程 ID
    JLabel jLabel6=new JLabel();  //阻塞队列
    JLabel jLabel7=new JLabel();  //执行进程中间结果
    JLabel jLabel8=new JLabel();  //磁盘目录结构
    JLabel jLabel9=new JLabel();  //正在执行的指令
    JLabel jLabel10=new JLabel(); //主存用户区使用情况
    JLabel jLabel11=new JLabel(); //设备使用情况
    JLabel jLabel12=new JLabel(); //进程执行完成，显示结果
    JLabel jLabel13=new JLabel(); //磁盘使用情况
    JLabel jLabel14=new JLabel(); //cpu

    JTextArea jTextArea1;
    JTextArea jTextArea2;
    JTextArea jTextArea3;
    JTextArea jTextArea5;
    JTextArea jTextArea6;
    JTextArea jTextArea7;
    JTextArea jTextArea9;
    JTextArea jTextArea10;
    JTextArea jTextArea11;
    JTextArea jTextArea12;

    public ProcessFrame(){
        //System.out.println(new main());
        setTitle("操作系统模拟");
        setLayout(null);
        setBounds(100,20,1150,700);
        Font font = new Font("宋体", Font.BOLD, 14);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image=tk.createImage("src/main/resources/jc.jpg");
        this.setIconImage(image);

        Container container =new Container();
        container.setLayout(null);

        //系统时钟
        jLabel1.setBounds(850,360,100,100);
        jLabel1.setLayout(null);
        add(jLabel1);

        jTextArea1= new JTextArea(3,15);
        jTextArea1.setBounds(10,20,80,70);
        jTextArea1.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea1.setEditable(false);

        //JScrollPane jScrollPane1 = new JScrollPane();
        //jScrollPane1.setBounds(10,20,210,92);
        jLabel1.setBorder(BorderFactory.createTitledBorder("系统时间"));
        //jScrollPane1.setViewportView(jTextArea1);
        jLabel1.add(jTextArea1);

        //cpu
        jLabel14.setBounds(25,500,130,130);
        jLabel14.setLayout(null);
        add(jLabel14);
        jLabel14.setBorder(BorderFactory.createTitledBorder("CPU"));
        Button button = new Button("CPU");
        button.setBounds(10,20,110,100);
        jLabel14.add(button);

        //时间片
        jLabel3.setBounds(960,360,100,100);
        jLabel3.setLayout(null);
        add(jLabel3);
        jTextArea3 = new JTextArea(3,20);
        jTextArea3.setBounds(10,20,80,70);
        jTextArea3.setEditable(false);
        jTextArea3.setFont(new Font("宋体",Font.BOLD,25));
        jLabel3.setBorder(BorderFactory.createTitledBorder("时间片"));
        jLabel3.add(jTextArea3);

        //就绪队列进程Uid
        jLabel5.setBounds(20,200,550,150);
        jLabel5.setLayout(null);
        add(jLabel5);
        jTextArea5 = new JTextArea(10,20);
        jTextArea5.setBounds(10,20,530,118);
        jTextArea5.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea5.setEditable(false);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setBounds(10,20,530,118);
        jLabel5.setBorder(BorderFactory.createTitledBorder("就绪队列进程id"));
        jScrollPane1.setViewportView(jTextArea5);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel5.add(jScrollPane1);

        //阻塞进程Uid
        jLabel6.setBounds(580,200,550,150);
        jLabel6.setLayout(null);
        add(jLabel6);
        jTextArea6 = new JTextArea(10,20);
        jTextArea6.setBounds(10,20,530,118);
        jTextArea6.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea6.setEditable(false);
        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setBounds(10,20,530,118);
        jLabel6.setBorder(BorderFactory.createTitledBorder("阻塞队列进程id"));
        jScrollPane2.setViewportView(jTextArea6);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel6.add(jScrollPane2);

        //执行进程中间结果
        jLabel7.setBounds(850,470,100,100);
        jLabel7.setLayout(null);
        add(jLabel7);
        jTextArea7 = new JTextArea(10,20);
        jTextArea7.setBounds(10,20,80,70);
        jTextArea7.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea7.setEditable(false);
       /* JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.setBounds(10,20,80,60);*/
        jLabel7.setBorder(BorderFactory.createTitledBorder("执行中间结果"));
       /* jScrollPane3.setViewportView(jTextArea7);
        jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);*/
        jLabel7.add(jTextArea7);

        //正在执行的指令
        jLabel9.setBounds(25,370,200,80);
        jLabel9.setLayout(null);
        add(jLabel9);
        jTextArea9 = new JTextArea(3,20);
        jTextArea9.setBounds(10,20,180,50);
        jTextArea9.setEditable(false);
        jTextArea9.setFont(new Font("宋体",Font.BOLD,25));
        jLabel9.setBorder(BorderFactory.createTitledBorder("正在执行的指令"));
        jLabel9.add(jTextArea9);

        //主存区使用情况
        jLabel10.setBounds(580,20,520,120);
        jLabel10.setLayout(null);
        add(jLabel10);
        jLabel10.setBorder(BorderFactory.createTitledBorder("主存区使用情况"));
        jTextArea10 = new JTextArea(10,20);
        jTextArea10.setBounds(10,20,493,90);
        jTextArea10.setEditable(false);
        jTextArea10.setFont(new Font("宋体",Font.BOLD,25));
        JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.setBounds(10,20,493,90);
        jScrollPane3.setViewportView(jTextArea10);
     //   jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      //  jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel10.add(jScrollPane3);

        //设备使用情况
        jLabel11.setBounds(230,360,600,300);
        jLabel11.setLayout(null);
        add(jLabel11);
        jTextArea11 = new JTextArea(10,20);
        jTextArea11.setBounds(10,20,580,270);
        jTextArea11.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea11.setEditable(false);
        /*JScrollPane jScrollPane4 = new JScrollPane();
        jScrollPane4.setBounds(10,20,380,170);*/
        jLabel11.setBorder(BorderFactory.createTitledBorder("设备使用情况"));
        /*jScrollPane4.setViewportView(jTextArea11);
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);*/
        jLabel11.add(jTextArea11);

        //显示结果
        jLabel12.setBounds(960,470,100,100);
        jLabel12.setLayout(null);
        add(jLabel12);
        jTextArea12 = new JTextArea(3,20);
        jTextArea12.setBounds(10,20,80,70);
        jTextArea12.setEditable(false);
        jTextArea12.setFont(new Font("宋体",Font.BOLD,25));
        jLabel12.setBorder(BorderFactory.createTitledBorder("结果"));
        jLabel12.add(jTextArea12);

        //正在运行的uid
        jLabel2.setBounds(20,20,550,150);
        jLabel2.setLayout(null);
        add(jLabel2);
        jTextArea2 = new JTextArea(10, 20);
        jTextArea2.setBounds(10,20,530,118);
        jTextArea2.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea2.setEditable(false);
        jLabel2.setBorder(BorderFactory.createTitledBorder("正在运行的Uid"));
        jLabel2.add(jTextArea2);




    }
    public  void printScreen(String a, String b, String c, String d,String e,String f,String g){
            jTextArea1.setText(a);
            jTextArea3.setText(b);
            jTextArea7.setText(c);
            jTextArea9.setText(d);
            jTextArea12.setText(e);
            jTextArea11.setText(f);
            jTextArea2.setText(g);
    }
    public  void printScreen2(ProcessScheduling processScheduling){
        if ( !processScheduling.getReadyPCB().isEmpty()) {
            Queue<PCB> readyPCB = processScheduling.getReadyPCB();
            Iterator<PCB> iterator = readyPCB.iterator();
            while (iterator.hasNext()) {
                PCB next = iterator.next();
                jTextArea5.setText(next.getUuid());
            }
        ArrayList<PCB> blockPCB = processScheduling.getBlockPCB();
        iterator = blockPCB.iterator();
        while (iterator.hasNext()){
            PCB next = iterator.next();
            jTextArea6.setText(next.getUuid());
        }
        }else
        {
            jTextArea5.setText(" ");
            jTextArea6.setText(" ");
        }
    }
    public void printScreen3(ProcessScheduling processScheduling){
        if (!processScheduling.getBlockPCB().isEmpty()) {
            for (PCB pcb : processScheduling.getBlockPCB()) {
                jTextArea6.setText(pcb.getUuid());
            }
        }
    }

    public void printScreen4(String a){
            /*Button button = new Button();
            button.setBounds(ss,20,holes.getSize(),50);
            jLabel10.add(button);*/
        jTextArea10.append(a);
    }
    public static void main(String[] args) throws IOException {
        ProcessFrame processFrame = new ProcessFrame();
        File test = new File("src/test/java/test");
        Reader reader = new FileReader(test);
        char[] s = new char[10000];
        reader.read(s);
        String s1 = String.valueOf(s);
        String[] split = s1.split("\r\n");
        Byte[][] files = new Byte[split.length-1][100];
        for (int i = 0; i < split.length-1; i++) {
            String[] s2 = split[i].split(" ");
            for (int j = 0; j < s2.length; j++) {
                files[i][j] = Util.compile(s2[j]);
            }
        }
        Memory memory = new Memory(512);
        Device device = new Device();
        ProcessScheduling processScheduling = new ProcessScheduling(memory,device);
        CPU cpu = new CPU(files.length, processScheduling,processScheduling.getIdlePCB().getUuid());
        CreatProcess creatProcess = new CreatProcess(files,processScheduling);
        TimeSchedul timeSchedul = new TimeSchedul(cpu);
        Thread thread = new Thread(creatProcess);
        Thread thread1 = new Thread(timeSchedul);
        Thread thread2 = new Thread(cpu);
        thread.start();
        thread1.start();
        thread2.start();
       /* for (Hole hole : memory.getHoles()) {
            processFrame.printScreen4(String.valueOf(hole.getHead())+" "+String.valueOf(hole.getSize())+" "+String.valueOf(hole.isFree()));
        }*/
        while (true) {
            int ss = 10;
            processFrame.printScreen(String.valueOf(main.SystemTime), String.valueOf(main.TimeSlice), String.valueOf(cpu.getAX()), cpu.getIR(), String.valueOf(cpu.getFinalAX()), device.getDeviceTable().toString(), processScheduling.getRunPCB().getUuid());
            processFrame.printScreen2(processScheduling);
            processFrame.printScreen3(processScheduling);

        }
    }
}
