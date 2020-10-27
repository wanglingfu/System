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
        setBounds(0,0,1920,1080);
        Font font = new Font("宋体", Font.BOLD, 14);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image=tk.createImage("src/frame/jc.jpg");
        this.setIconImage(image);

        Container co =new Container();
        co.setLayout(null);

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
        jLabel1.setBounds(10,20,200,50);
        jLabel1.setLayout(null);
        add(jLabel1);
        final JTextArea p1_info=new JTextArea(" abc",1,5);
        p1_info.setBounds(10,20,740,280);
        p1_info.setFont(new Font("宋体", Font.BOLD, 30));
        p1_info.setEditable(false);

        JScrollPane p1_jscroll=new JScrollPane();
        p1_jscroll.setViewportView(p1_info);
        p1_jscroll.setBounds(10,20,740,280);
        jLabel1.add(p1_jscroll);



    }

    public static void main(String[] args) {

        new ProcessFrame();
    }
}
