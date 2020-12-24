package Main;

import frame.FileManagement.FileUtil;
import frame.deviceManagement.Device;
import frame.processManagement.PCB;
import frame.processManagement.ProcessScheduling;
import frame.processManagement.Runnable.CPU;
import frame.processManagement.Runnable.CreatProcess;
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
import java.util.concurrent.*;

/**
 * @ClassName ProcessFrame
 * @Description 操作系统界面
 * @Author wlf
 * @Date 2020/10/16 21:53
 * @Version 1.0
 **/
public class ProcessFrame extends JFrame {
    private static Timer timer;
    public static int bool = 0;
    private  String string = null;
    private static final long serialVersionUID = 1L;
    private static int dp_method=0;
    private final JLabel jLabelTime =new JLabel();  //系统时钟
    private final JLabel jLabelPCBRun =new JLabel();  //正在运行进程id
    private final JLabel jLabelTimeSlice =new JLabel();  //时间片
    private final JLabel jLabelPCBReady =new JLabel();  //就 绪 队 列 进 程 ID
    private final JLabel jLabelPCBBlock =new JLabel();  //阻塞队列
    private final JLabel jLabelIntermediateResults=new JLabel();  //执行进程中间结果
    private final JLabel jlabelExecuting =new JLabel();  //正在执行的指令
    private final JLabel jLabel1MainMemory =new JLabel(); //主存用户区使用情况
    private final JLabel jLabelDevice =new JLabel(); //设备使用情况
    private final JLabel jLabelResult=new JLabel(); //进程执行完成，显示结果
    private final JLabel jLabelCPU =new JLabel(); //cpu
    private final JTextArea jTextAreaTime;
    private final JTextArea jTextAreaPCBRun;
    private final JTextArea jTextAreaTimeSlice;
    private final JTextArea jTextAreaPCBReady;
    private final JTextArea jTextAreaPCBBlock;
    private final JTextArea jTextAreaIntermediateResults;
    private final JTextArea jTextAreaExecuting;
    private final JTextArea jTextAreaDevice;
    private final JTextArea jTextAreaResult;

    public ProcessFrame(){
        setTitle("操作系统模拟");
        setLayout(null);
        setBounds(100,20,1150,700);
        Font font = new Font("宋体", Font.BOLD, 14);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image=tk.createImage("src/main/resources/jc.jpg");
        this.setIconImage(image);
        Container container =new Container();
        container.setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBackground(Color.white);
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
        jLabelCPU.add(button);
        button.setBounds(10,20,110,100);
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
        jLabelPCBBlock.setBounds(575,140,560+5,150+60);
        jLabelPCBBlock.setLayout(null);
        add(jLabelPCBBlock);
        jTextAreaPCBBlock = new JTextArea(10,20);
        jTextAreaPCBBlock.setBounds(10,20,545-10-5,118+60);
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
        jLabelIntermediateResults.setBorder(BorderFactory.createTitledBorder("执行中间结果"));
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
        jTextAreaPCBRun.setBounds(10+5,20+5,520,60);
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
    public void printScreen3(String a1, int a1time,String a2, int a2time,String b1,int b1time, String b2,
                             int b2time,String b3,int b3time, String c1,int c1time, String c2,int c2time,
                             String c3,int c3time){
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
            string = null;
            Hole hole = holes.get(i);
            if(hole.getUid() != null)
            {
                string = hole.getUid().substring(9,13);
            }
            Button button = new Button(string);
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
    }
    public static  void setVisibleTest(ProcessFrame processFrame){
        processFrame.setVisible(true);
    }
    public static void test(ProcessFrame processFrame) throws Exception {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }

        Memory memory = new Memory(512);
        Device device = new Device();
        ProcessScheduling processScheduling = new ProcessScheduling(memory,device);
        FileUtil fileUtil = new FileUtil();
        ArrayList<String> exeFiles = fileUtil.getExeFiles();
        CPU cpu = new CPU(exeFiles.size(), processScheduling,processScheduling.getIdlePCB().getUuid());
        CreatProcess creatProcess = new CreatProcess(exeFiles,processScheduling,fileUtil);
        processFrame.printScreen(String.valueOf(cpu.SystemTime), String.valueOf(cpu.TimeSlice), String.valueOf(cpu.getAX()), cpu.getIR(), String.valueOf(cpu.getFinalAX()), device.getDeviceTable().toString(), processScheduling.getRunPCB().getUuid());
        processFrame.printScreen2(processScheduling);
        processFrame.printScreen4(memory.getHoles());
        processFrame.printScreen3(device.getDeviceTable().getA1(), cpu.DeviceTime[0], device.getDeviceTable().getA2(), cpu.DeviceTime[1], device.getDeviceTable().getB1(), cpu.DeviceTime[2], device.getDeviceTable().getB2(), cpu.DeviceTime[3], device.getDeviceTable().getB3(), cpu.DeviceTime[4], device.getDeviceTable().getC1(),
                cpu.DeviceTime[5], device.getDeviceTable().getC2(), cpu.DeviceTime[6], device.getDeviceTable().getC3(), cpu.DeviceTime[7]);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(()->{
            try {
                cpu.cpu();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(()->{
            creatProcess.run();
        });
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            cpu.time();
            if(cpu.getFlag() == 0){
                Thread.currentThread().stop();
            }
        },1,1,TimeUnit.SECONDS);
        int timerDelay = 10;
        timer = new Timer(timerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bool = 0;
                CPU.lock.lock();
                try {
                    CPU.condition2.await();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                processFrame.printScreen(String.valueOf(cpu.SystemTime), String.valueOf(cpu.TimeSlice), String.valueOf(cpu.getAX()), cpu.getIR(), String.valueOf(cpu.getFinalAX()), device.getDeviceTable().toString(), processScheduling.getRunPCB().getUuid());
                processFrame.printScreen2(processScheduling);
                processFrame.printScreen4(memory.getHoles());
                processFrame.printScreen3(device.getDeviceTable().getA1(), cpu.DeviceTime[0], device.getDeviceTable().getA2(), cpu.DeviceTime[1], device.getDeviceTable().getB1(), cpu.DeviceTime[2], device.getDeviceTable().getB2(), cpu.DeviceTime[3], device.getDeviceTable().getB3(), cpu.DeviceTime[4], device.getDeviceTable().getC1(),
                        cpu.DeviceTime[5], device.getDeviceTable().getC2(), cpu.DeviceTime[6], device.getDeviceTable().getC3(), cpu.DeviceTime[7]);
                CPU.lock.unlock();
                if(cpu.getFlag() == 0){
                    bool = 1;
                    timer.stop();
                }
            }
        });
        timer.start();
    }

}
