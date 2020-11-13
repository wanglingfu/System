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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int dp_method=0;
    private JLabel jLabelTime =new JLabel();  //系统时钟
    private JLabel jLabelPCBRun =new JLabel();  //正在运行进程id
    private JLabel jLabelTimeSlice =new JLabel();  //时间片
    private JLabel jLabelPCBReady =new JLabel();  //就 绪 队 列 进 程 ID
    private JLabel jLabelPCBBlock =new JLabel();  //阻塞队列
    private JLabel jLabelIntermediateResults=new JLabel();  //执行进程中间结果
    private JLabel jlabelExecuting =new JLabel();  //正在执行的指令
    private JLabel jLabel1MainMemory =new JLabel(); //主存用户区使用情况
    private JLabel jLabelDevice =new JLabel(); //设备使用情况
    private JLabel jLabelResult=new JLabel(); //进程执行完成，显示结果
    private JLabel jLabelCPU =new JLabel(); //cpu

    private JTextArea jTextAreaTime;
    private JTextArea jTextAreaPCBRun;
    private JTextArea jTextAreaTimeSlice;
    private JTextArea jTextAreaPCBReady;
    private JTextArea jTextAreaPCBBlock;
    private JTextArea jTextAreaIntermediateResults;
    private JTextArea jTextAreaExecuting;
    private JTextArea jTextAreaMainMemory;
    private JTextArea jTextAreaDevice;
    private JTextArea jTextAreaResult;

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
        jLabelTime.setBounds(850+50,360,100,100);
        jLabelTime.setLayout(null);
        add(jLabelTime);

        jTextAreaTime = new JTextArea(1,15);
        jTextAreaTime.setBounds(10,20,80,70);
        jTextAreaTime.setFont(new Font("宋体",Font.BOLD,50));
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
        jLabelTimeSlice.setBounds(960+50,360,100,100);
        jLabelTimeSlice.setLayout(null);
        add(jLabelTimeSlice);
        jTextAreaTimeSlice = new JTextArea(1,20);
        jTextAreaTimeSlice.setBounds(10,20,80,70);
        jTextAreaTimeSlice.setEditable(false);
        jTextAreaTimeSlice.setFont(new Font("宋体",Font.BOLD,50));
        jLabelTimeSlice.setBorder(BorderFactory.createTitledBorder("时间片"));
        jLabelTimeSlice.add(jTextAreaTimeSlice);

        //就绪队列进程Uid
        jLabelPCBReady.setBounds(20,140,550,150+60);
        jLabelPCBReady.setLayout(null);
        add(jLabelPCBReady);
        jTextAreaPCBReady = new JTextArea(10,20);
        jTextAreaPCBReady.setBounds(10,20,530,118+60);
        jTextAreaPCBReady.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaPCBReady.setEditable(false);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setBounds(10,20,530,118+60);
        jLabelPCBReady.setBorder(BorderFactory.createTitledBorder("就绪队列进程id"));
        jScrollPane1.setViewportView(jTextAreaPCBReady);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabelPCBReady.add(jScrollPane1);

        //阻塞进程Uid
        jLabelPCBBlock.setBounds(575,140,560,150+60);
        jLabelPCBBlock.setLayout(null);
        add(jLabelPCBBlock);
        jTextAreaPCBBlock = new JTextArea(10,20);
        jTextAreaPCBBlock.setBounds(10,20,545,118+60);
        jTextAreaPCBBlock.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaPCBBlock.setEditable(false);
        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setBounds(10,20,545,118+60);
        jLabelPCBBlock.setBorder(BorderFactory.createTitledBorder("阻塞队列进程id"));
        jScrollPane2.setViewportView(jTextAreaPCBBlock);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabelPCBBlock.add(jScrollPane2);

        //执行进程中间结果
        jLabelIntermediateResults.setBounds(850+50,470,100,100);
        jLabelIntermediateResults.setLayout(null);
        add(jLabelIntermediateResults);
        jTextAreaIntermediateResults = new JTextArea(1,20);
        jTextAreaIntermediateResults.setBounds(10,20,80,70);
        jTextAreaIntermediateResults.setFont(new Font("宋体",Font.BOLD,50));
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
        jTextAreaExecuting = new JTextArea(1,20);
        jTextAreaExecuting.setBounds(10,20,180,50);
        jTextAreaExecuting.setEditable(false);
        jTextAreaExecuting.setFont(new Font("宋体",Font.BOLD,40));
        jlabelExecuting.setBorder(BorderFactory.createTitledBorder("正在执行的指令"));
        jlabelExecuting.add(jTextAreaExecuting);

        //主存区使用情况
        jLabel1MainMemory.setBounds(580,20,520,100);
        jLabel1MainMemory.setLayout(null);
        add(jLabel1MainMemory);
        jLabel1MainMemory.setBorder(BorderFactory.createTitledBorder("主存区使用情况"));
       /* jTextAreaMainMemory = new JTextArea(10,20);
        jTextAreaMainMemory.setBounds(10,80,493,90);
        jTextAreaMainMemory.setEditable(false);
        jTextAreaMainMemory.setFont(new Font("宋体",Font.BOLD,25));
        JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.setBounds(10,80,493,90);
        jScrollPane3.setViewportView(jTextAreaMainMemory);
        jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel1MainMemory.add(jScrollPane3);*/

        //设备使用情况
        jLabelDevice.setBounds(230,360,600+50,300);
        jLabelDevice.setLayout(null);
        add(jLabelDevice);
        jTextAreaDevice = new JTextArea(10,20);
        jTextAreaDevice.setBounds(10,20,580+50,270);
        jTextAreaDevice.setFont(new Font("宋体",Font.BOLD,25));
        jTextAreaDevice.setEditable(false);
        JScrollPane jScrollPane4 = new JScrollPane();
        jScrollPane4.setBounds(10,20,580+50,270);
        jLabelDevice.setBorder(BorderFactory.createTitledBorder("设备使用情况"));
        jScrollPane4.setViewportView(jTextAreaDevice);
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabelDevice.add(jScrollPane4);

        //显示结果
        jLabelResult.setBounds(960+50,470,100,100);
        jLabelResult.setLayout(null);
        add(jLabelResult);
        jTextAreaResult = new JTextArea(1,20);
        jTextAreaResult.setBounds(10,20,80,70);
        jTextAreaResult.setEditable(false);
        jTextAreaResult.setFont(new Font("宋体",Font.BOLD,50));
        jLabelResult.setBorder(BorderFactory.createTitledBorder("结果"));
        jLabelResult.add(jTextAreaResult);

        //正在运行的uid
        jLabelPCBRun.setBounds(20,20,550,100);
        jLabelPCBRun.setLayout(null);
        add(jLabelPCBRun);
        jTextAreaPCBRun = new JTextArea(1, 20);
        jTextAreaPCBRun.setBounds(10,20,530,60);
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
           // jTextAreaDevice.setText(f);
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
        }else
        {
            jTextAreaPCBReady.setText("");
        }
        jTextAreaPCBBlock.setText("");
        if ( !processScheduling.getBlockPCB().isEmpty()){

            ArrayList<PCB> blockPCB = processScheduling.getBlockPCB();
            Iterator<PCB> iterator = blockPCB.iterator();
            while (iterator.hasNext()){
                PCB next = iterator.next();
                jTextAreaPCBBlock.append(next.getUuid()+" "+next.getReason()+"\n");
            }
        }
        else{
            jTextAreaPCBBlock.setText("");
        }
    }
    public void printScreen3(String a1, int a1time,String a2, int a2time,String b1,int b1time, String b2, int b2time,String b3,int b3time, String c1,int c1time, String c2,int c2time, String c3,int c3time){
        jTextAreaDevice.setText("");
        if(a1time < 0)
            a1time = 0;
        if(a2time < 0)
            a2time = 0;
        if(b1time < 0)
            b1time = 0;
        if(b2time < 0)
            b2time = 0;
        if(b3time < 0)
            b3time = 0;
        if(c1time < 0)
            c1time = 0;
        if(c2time < 0)
            c2time = 0;
        if(c3time < 0)
            c3time = 0;
        jTextAreaDevice.append("设备A:"+a1+" "+String.valueOf(a1time)+"\n");
        jTextAreaDevice.append("设备A:"+a2+" "+String.valueOf(a2time)+"\n");
        jTextAreaDevice.append("设备B:"+b1+" "+String.valueOf(b1time)+"\n");
        jTextAreaDevice.append("设备B:"+b2+" "+String.valueOf(b2time)+"\n");
        jTextAreaDevice.append("设备B:"+b3+" "+String.valueOf(b3time)+"\n");
        jTextAreaDevice.append("设备C:"+c1+" "+String.valueOf(c1time)+"\n");
        jTextAreaDevice.append("设备C:"+c2+" "+String.valueOf(c2time)+"\n");
        jTextAreaDevice.append("设备C:"+c3+" "+String.valueOf(c3time)+"\n");
    }
    public void printScreen4(LinkedList<Hole> holes){
            jLabel1MainMemory.removeAll();
            int ss = 4;
            for (int i = 0; i < holes.size(); i++){
                Hole hole = holes.get(i);
                Button button = new Button(hole.getUid());
                if (hole.isFree()){
                    button.setBackground(Color.BLACK);
                }
                else {
                    button.setBackground(Color.red);
                }
                jLabel1MainMemory.add(button);
                button.setBounds(ss,25,hole.getSize(),50);
                ss += hole.getSize();
        }

       //jTextAreaMainMemory.append(a);
    }
    public static void test() throws IOException {
        final ProcessFrame processFrame = new ProcessFrame();
        File test = new File("src\\test\\java\\test");
        Reader reader = new FileReader(test);
        char[] s = new char[10000];
        reader.read(s);
        String s1 = String.valueOf(s);
        String[] split = s1.split("\r\n");
        byte[][] files = new byte[split.length][100];
        for (int i = 0; i < split.length; i++) {
            String[] s2 = split[i].split(" ");
            for (int j = 0; j < s2.length; j++) {
                if(j == s2.length-1)
                    files[i][j] = Util.compile("end");
                else
                    files[i][j] = Util.compile(s2[j]);
            }
        }
        final Memory memory = new Memory(512);
        final Device device = new Device();
        final ProcessScheduling processScheduling = new ProcessScheduling(memory,device);
        final CPU cpu = new CPU(files.length, processScheduling,processScheduling.getIdlePCB().getUuid());
        CreatProcess creatProcess = new CreatProcess(files,processScheduling);
        TimeSchedul timeSchedul = new TimeSchedul(cpu,processScheduling);
        Thread thread = new Thread(creatProcess);
        Thread thread1 = new Thread(timeSchedul);
        Thread thread2 = new Thread(cpu);
        thread.start();
        thread1.start();
        thread2.start();
        int timerDelay = 100;
        new Timer(timerDelay, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                processFrame.printScreen(String.valueOf(main.SystemTime), String.valueOf(main.TimeSlice), String.valueOf(cpu.getAX()), cpu.getIR(), String.valueOf(cpu.getFinalAX()), device.getDeviceTable().toString(), processScheduling.getRunPCB().getUuid());
                processFrame.printScreen2(processScheduling);
                processFrame.printScreen4(memory.getHoles());
                processFrame.printScreen3(device.getDeviceTable().getA1(),main.DeviceTime[0],device.getDeviceTable().getA2(),main.DeviceTime[1],device.getDeviceTable().getB1(),main.DeviceTime[2],device.getDeviceTable().getB2(),main.DeviceTime[3],device.getDeviceTable().getB3(),main.DeviceTime[4],device.getDeviceTable().getC1(),
                        main.DeviceTime[5],device.getDeviceTable().getC2(),main.DeviceTime[6],device.getDeviceTable().getC3(),main.DeviceTime[7]);
            }
        }).start();
    }

}
