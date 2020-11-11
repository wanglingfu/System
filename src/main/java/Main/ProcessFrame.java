package Main;

import frame.deviceManagement.Device;
import frame.processManagement.PCB;
import frame.processManagement.ProcessScheduling;
import frame.processManagement.Runnable.CPU;
import frame.processManagement.Runnable.CreatProcess;
import frame.processManagement.Runnable.TimeSchedul;
import frame.processManagement.Util;
import frame.storageManagement.Memory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class ProcessFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int dp_method=0;
    JLabel jLabelTime =new JLabel();  //系统时钟
    JLabel jLabelPCBRun =new JLabel();  //正在运行进程id
    JLabel jLabelTimeSlice =new JLabel();  //时间片
    JLabel jLabelPCBReady =new JLabel();  //就 绪 队 列 进 程 ID
    JLabel jLabelPCBBlock =new JLabel();  //阻塞队列
    JLabel jLabelIntermediateResults=new JLabel();  //执行进程中间结果
    JLabel jlabelExecuting =new JLabel();  //正在执行的指令
    JLabel jLabel1MainMemory =new JLabel(); //主存用户区使用情况
    JLabel jLabelDevice =new JLabel(); //设备使用情况
    JLabel jLabelResult=new JLabel(); //进程执行完成，显示结果
    JLabel jLabelCPU =new JLabel(); //cpu

    JTextArea jTextAreaTime;
    JTextArea jTextAreaPCBRun;
    JTextArea jTextAreaTimeSlice;
    JTextArea jTextAreaPCBReady;
    JTextArea jTextAreaPCBBlock;
    JTextArea jTextAreaIntermediateResults;
    JTextArea jTextAreaExecuting;
    JTextArea jTextAreaMainMemory;
    JTextArea jTextAreaDevice;
    JTextArea jTextAreaResult;

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
        jLabelTime.setBounds(850,360,100,100);
        jLabelTime.setLayout(null);
        add(jLabelTime);

        jTextAreaTime = new JTextArea(3,15);
        jTextAreaTime.setBounds(10,20,80,70);
        jTextAreaTime.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaTime.setEditable(false);

        //JScrollPane jScrollPane1 = new JScrollPane();
        //jScrollPane1.setBounds(10,20,210,92);
        jLabelTime.setBorder(BorderFactory.createTitledBorder("系统时间"));
        //jScrollPane1.setViewportView(jTextArea1);
        jLabelTime.add(jTextAreaTime);

        //cpu
        jLabelCPU.setBounds(25,500,130,130);
        jLabelCPU.setLayout(null);
        add(jLabelCPU);
        jLabelCPU.setBorder(BorderFactory.createTitledBorder("CPU"));
        Button button = new Button("CPU");
        button.setBounds(10,20,110,100);
        jLabelCPU.add(button);

        //时间片
        jLabelTimeSlice.setBounds(960,360,100,100);
        jLabelTimeSlice.setLayout(null);
        add(jLabelTimeSlice);
        jTextAreaTimeSlice = new JTextArea(3,20);
        jTextAreaTimeSlice.setBounds(10,20,80,70);
        jTextAreaTimeSlice.setEditable(false);
        jTextAreaTimeSlice.setFont(new Font("宋体",Font.BOLD,25));
        jLabelTimeSlice.setBorder(BorderFactory.createTitledBorder("时间片"));
        jLabelTimeSlice.add(jTextAreaTimeSlice);

        //就绪队列进程Uid
        jLabelPCBReady.setBounds(20,200,550,150);
        jLabelPCBReady.setLayout(null);
        add(jLabelPCBReady);
        jTextAreaPCBReady = new JTextArea(10,20);
        jTextAreaPCBReady.setBounds(10,20,530,118);
        jTextAreaPCBReady.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaPCBReady.setEditable(false);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setBounds(10,20,530,118);
        jLabelPCBReady.setBorder(BorderFactory.createTitledBorder("就绪队列进程id"));
        jScrollPane1.setViewportView(jTextAreaPCBReady);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabelPCBReady.add(jScrollPane1);

        //阻塞进程Uid
        jLabelPCBBlock.setBounds(580,200,550,150);
        jLabelPCBBlock.setLayout(null);
        add(jLabelPCBBlock);
        jTextAreaPCBBlock = new JTextArea(10,20);
        jTextAreaPCBBlock.setBounds(10,20,530,118);
        jTextAreaPCBBlock.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaPCBBlock.setEditable(false);
        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setBounds(10,20,530,118);
        jLabelPCBBlock.setBorder(BorderFactory.createTitledBorder("阻塞队列进程id"));
        jScrollPane2.setViewportView(jTextAreaPCBBlock);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabelPCBBlock.add(jScrollPane2);

        //执行进程中间结果
        jLabelIntermediateResults.setBounds(850,470,100,100);
        jLabelIntermediateResults.setLayout(null);
        add(jLabelIntermediateResults);
        jTextAreaIntermediateResults = new JTextArea(10,20);
        jTextAreaIntermediateResults.setBounds(10,20,80,70);
        jTextAreaIntermediateResults.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaIntermediateResults.setEditable(false);
       /* JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.setBounds(10,20,80,60);*/
        jLabelIntermediateResults.setBorder(BorderFactory.createTitledBorder("执行中间结果"));
       /* jScrollPane3.setViewportView(jTextArea7);
        jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);*/
        jLabelIntermediateResults.add(jTextAreaIntermediateResults);

        //正在执行的指令
        jlabelExecuting.setBounds(25,370,200,80);
        jlabelExecuting.setLayout(null);
        add(jlabelExecuting);
        jTextAreaExecuting = new JTextArea(3,20);
        jTextAreaExecuting.setBounds(10,20,180,50);
        jTextAreaExecuting.setEditable(false);
        jTextAreaExecuting.setFont(new Font("宋体",Font.BOLD,25));
        jlabelExecuting.setBorder(BorderFactory.createTitledBorder("正在执行的指令"));
        jlabelExecuting.add(jTextAreaExecuting);

        //主存区使用情况
        jLabel1MainMemory.setBounds(580,20,520,120);
        jLabel1MainMemory.setLayout(null);
        add(jLabel1MainMemory);
        jLabel1MainMemory.setBorder(BorderFactory.createTitledBorder("主存区使用情况"));
        jTextAreaMainMemory = new JTextArea(10,20);
        jTextAreaMainMemory.setBounds(10,20,493,90);
        jTextAreaMainMemory.setEditable(false);
        jTextAreaMainMemory.setFont(new Font("宋体",Font.BOLD,25));
        JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.setBounds(10,20,493,90);
        jScrollPane3.setViewportView(jTextAreaMainMemory);
     //   jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      //  jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel1MainMemory.add(jScrollPane3);

        //设备使用情况
        jLabelDevice.setBounds(230,360,600,300);
        jLabelDevice.setLayout(null);
        add(jLabelDevice);
        jTextAreaDevice = new JTextArea(10,20);
        jTextAreaDevice.setBounds(10,20,580,270);
        jTextAreaDevice.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaDevice.setEditable(false);
        /*JScrollPane jScrollPane4 = new JScrollPane();
        jScrollPane4.setBounds(10,20,380,170);*/
        jLabelDevice.setBorder(BorderFactory.createTitledBorder("设备使用情况"));
        /*jScrollPane4.setViewportView(jTextArea11);
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);*/
        jLabelDevice.add(jTextAreaDevice);

        //显示结果
        jLabelResult.setBounds(960,470,100,100);
        jLabelResult.setLayout(null);
        add(jLabelResult);
        jTextAreaResult = new JTextArea(3,20);
        jTextAreaResult.setBounds(10,20,80,70);
        jTextAreaResult.setEditable(false);
        jTextAreaResult.setFont(new Font("宋体",Font.BOLD,25));
        jLabelResult.setBorder(BorderFactory.createTitledBorder("结果"));
        jLabelResult.add(jTextAreaResult);

        //正在运行的uid
        jLabelPCBRun.setBounds(20,20,550,150);
        jLabelPCBRun.setLayout(null);
        add(jLabelPCBRun);
        jTextAreaPCBRun = new JTextArea(10, 20);
        jTextAreaPCBRun.setBounds(10,20,530,118);
        jTextAreaPCBRun.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaPCBRun.setEditable(false);
        jLabelPCBRun.setBorder(BorderFactory.createTitledBorder("正在运行的Uid"));
        jLabelPCBRun.add(jTextAreaPCBRun);




    }
    public  void printScreen(String a, String b, String c, String d,String e,String f,String g){
            jTextAreaTime.setText(a);
            jTextAreaTimeSlice.setText(b);
            jTextAreaIntermediateResults.setText(c);
            jTextAreaExecuting.setText(d);
            jTextAreaResult.setText(e);
            jTextAreaDevice.setText(f);
            jTextAreaPCBRun.setText(g);
    }
    public  void printScreen2(ProcessScheduling processScheduling){
        jTextAreaPCBReady.setText("");
        if ( !processScheduling.getReadyPCB().isEmpty()) {
            Queue<PCB> readyPCB = processScheduling.getReadyPCB();
            Iterator<PCB> iterator = readyPCB.iterator();
            while (iterator.hasNext()) {
                PCB next = iterator.next();
                jTextAreaPCBReady.append(next.getUuid()+"\n");
            }
        ArrayList<PCB> blockPCB = processScheduling.getBlockPCB();
        iterator = blockPCB.iterator();
        while (iterator.hasNext()){
            PCB next = iterator.next();
            jTextAreaPCBBlock.append(next.getUuid()+"\n");
        }
        }else
        {
            jTextAreaPCBReady.setText("");
            jTextAreaPCBBlock.setText("");
        }
    }
    public void printScreen4(String a){
            /*Button button = new Button();
            button.setBounds(ss,20,holes.getSize(),50);
            jLabel10.add(button);*/
        jTextAreaMainMemory.append(a);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
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
        TimeSchedul timeSchedul = new TimeSchedul(cpu,processScheduling);
        Thread thread = new Thread(creatProcess);
        Thread thread1 = new Thread(timeSchedul);
        Thread thread2 = new Thread(cpu);
        thread.start();
        thread1.start();
        thread2.start();
       /* for (Hole hole : memory.getHoles()) {
            processFrame.printScreen4(String.valueOf(hole.getHead())+" "+String.valueOf(hole.getSize())+" "+String.valueOf(hole.isFree()));
        }*/
        System.out.println(processScheduling.getIdlePCB().getUuid());
        while (true) {
            Thread.sleep(500);
            processFrame.printScreen(String.valueOf(main.SystemTime), String.valueOf(main.TimeSlice), String.valueOf(cpu.getAX()), cpu.getIR(), String.valueOf(cpu.getFinalAX()), device.getDeviceTable().toString(), processScheduling.getRunPCB().getUuid());
            processFrame.printScreen2(processScheduling);
        }
    }
}
