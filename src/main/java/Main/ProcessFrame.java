package Main;

import javax.swing.*;
import java.awt.*;

public class ProcessFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int dp_method=0;

    public ProcessFrame(){
        System.out.println(new main());
        setTitle("操作系统模拟");
        setLayout(null);
        setBounds(100,20,1150,700);
        Font font = new Font("宋体", Font.BOLD, 14);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image=tk.createImage("src/frame/jc.jpg");
        this.setIconImage(image);

        Container container =new Container();
        container.setLayout(null);

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


        //系统时钟
        jLabel1.setBounds(45,50,230,120);
        jLabel1.setLayout(null);
        add(jLabel1);

        final JTextArea jTextArea1= new JTextArea("123456789123456789",3,15);
        jTextArea1.setBounds(10,20,210,92);
        jTextArea1.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea1.setEditable(false);

        //JScrollPane jScrollPane1 = new JScrollPane();
        //jScrollPane1.setBounds(10,20,210,92);
        jLabel1.setBorder(BorderFactory.createTitledBorder("系统时间"));
        //jScrollPane1.setViewportView(jTextArea1);
        jLabel1.add(jTextArea1);

        //cpu
        jLabel14.setBounds(350,50,130,130);
        jLabel14.setLayout(null);
        add(jLabel14);
        jLabel14.setBorder(BorderFactory.createTitledBorder("CPU"));
        Button button = new Button("CPU");
        button.setBounds(10,20,110,100);
        jLabel14.add(button);

        //时间片
        jLabel3.setBounds(550,50,300,120);
        jLabel3.setLayout(null);
        add(jLabel3);
        final JTextArea jTextArea3 = new JTextArea("test",3,20);
        jTextArea3.setBounds(10,20,280,90);
        jTextArea3.setEditable(false);
        jTextArea3.setFont(new Font("宋体",Font.BOLD,25));
        jLabel3.setBorder(BorderFactory.createTitledBorder("时间片"));
        jLabel3.add(jTextArea3);

        //就绪队列进程Uid
        jLabel5.setBounds(45,200,200,150);
        jLabel5.setLayout(null);
        add(jLabel5);
        final JTextArea jTextArea5 = new JTextArea("test",10,20);
        jTextArea5.setBounds(10,20,180,118);
        jTextArea5.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea5.setEditable(true);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setBounds(10,20,180,118);
        jLabel5.setBorder(BorderFactory.createTitledBorder("就绪队列进程id"));
        jScrollPane1.setViewportView(jTextArea5);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel5.add(jScrollPane1);

        //阻塞进程Uid
        jLabel6.setBounds(350,200,200,150);
        jLabel6.setLayout(null);
        add(jLabel6);
        final JTextArea jTextArea6 = new JTextArea("test",10,20);
        jTextArea6.setBounds(10,20,180,118);
        jTextArea6.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea6.setEditable(true);
        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setBounds(10,20,180,118);
        jLabel6.setBorder(BorderFactory.createTitledBorder("阻塞队列进程id"));
        jScrollPane2.setViewportView(jTextArea5);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel6.add(jScrollPane2);

        //执行进程中间结果
        jLabel7.setBounds(600,200,400,200);
        jLabel7.setLayout(null);
        add(jLabel7);
        final JTextArea jTextArea7 = new JTextArea("test",10,20);
        jTextArea7.setBounds(10,20,375,175);
        jTextArea7.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea7.setEditable(true);
        JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.setBounds(10,20,380,170);
        jLabel7.setBorder(BorderFactory.createTitledBorder("执行中间结果"));
        jScrollPane3.setViewportView(jTextArea5);
        jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel7.add(jScrollPane3);

        //正在执行的指令
        jLabel9.setBounds(50,400,300,120);
        jLabel9.setLayout(null);
        add(jLabel9);
        final JTextArea jTextArea9 = new JTextArea("test",3,20);
        jTextArea9.setBounds(10,20,280,90);
        jTextArea9.setEditable(false);
        jTextArea9.setFont(new Font("宋体",Font.BOLD,25));
        jLabel9.setBorder(BorderFactory.createTitledBorder("正在执行的指令"));
        jLabel9.add(jTextArea9);

        //主存区使用情况
        jLabel10.setBounds(50,530,300,120);
        jLabel10.setLayout(null);
        add(jLabel10);
        final JTextArea jTextArea10 = new JTextArea("test",3,20);
        jTextArea10.setBounds(10,20,280,90);
        jTextArea10.setEditable(false);
        jTextArea10.setFont(new Font("宋体",Font.BOLD,25));
        jLabel10.setBorder(BorderFactory.createTitledBorder("主存区使用情况"));
        jLabel10.add(jTextArea10);

        //设备使用情况
        jLabel11.setBounds(400,425,400,200);
        jLabel11.setLayout(null);
        add(jLabel11);
        final JTextArea jTextArea11 = new JTextArea("test",10,20);
        jTextArea11.setBounds(10,20,375,175);
        jTextArea11.setFont(new Font("宋体",Font.BOLD,25));
        jTextArea11.setEditable(true);
        JScrollPane jScrollPane4 = new JScrollPane();
        jScrollPane4.setBounds(10,20,380,170);
        jLabel11.setBorder(BorderFactory.createTitledBorder("设备使用情况"));
        jScrollPane4.setViewportView(jTextArea11);
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jLabel11.add(jScrollPane4);

        //显示结果
        jLabel12.setBounds(850,420,200,200);
        jLabel12.setLayout(null);
        add(jLabel12);
        final JTextArea jTextArea12 = new JTextArea("test",3,20);
        jTextArea12.setBounds(10,20,180,170);
        jTextArea12.setEditable(false);
        jTextArea12.setFont(new Font("宋体",Font.BOLD,25));
        jLabel12.setBorder(BorderFactory.createTitledBorder("结果"));
        jLabel12.add(jTextArea12);

    }

    public static void main(String[] args) {

        new ProcessFrame();
    }
}
