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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class main extends JFrame {
    public static final ReentrantLock lockTime = new ReentrantLock();
    public static final ReentrantLock lockCreate = new ReentrantLock();
    public static int SystemTime=0;//系统时间
    public static int TimeSlice=6;//时间片
    public static int[] DeviceTime = {-1,-1,-1,-1,-1,-1,-1,-1};//设备时间
    private static final long serialVersionUID=1L;
    public main(){
        setResizable(false);
        setBounds(250,17,900,700);
        setTitle("Double-Z");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        JFrame frame = new main();
        String plaf="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try{
            UIManager.setLookAndFeel(plaf);
            SwingUtilities.updateComponentTreeUI(frame);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        frame.setVisible(true);
        frame.setLayout(null);
        //背景
        ImageIcon img = new ImageIcon("src/main/resources/bg.jpg");
        JLabel imgLabel=new JLabel(img);
        frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
        JPanel jp = (JPanel)frame.getContentPane();
        jp.setOpaque(false);
        //文件管理图标
        JButton button1=new JButton();
        button1.setBounds(20, 20, 50, 50);
        ImageIcon logo1=new ImageIcon("src/main/resources/file.png");
        button1.setIcon(logo1);
        frame.add(button1);
        button1.setBackground(Color.white);
        JLabel lb1=new JLabel("文件");
        lb1.setBounds(30,72,100,20);
        frame.add(lb1);
        frame.setLayout(null);
        lb1.setForeground(Color.white);
        lb1.setBackground(Color.white);
        lb1.setFont(new Font("宋体", Font.BOLD, 14));
        button1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        //存储管理
        JButton button2=new JButton();
        button2.setBounds(20, 120, 50, 50);
        ImageIcon logo2=new ImageIcon("src/main/resources/bt1.jpg");
        button2.setIcon(logo2);
        frame.add(button2);
        button2.setBackground(Color.white);
        JLabel lb2=new JLabel("存储");
        lb2.setBounds(30,172,100,20);
        frame.add(lb2);
        frame.setLayout(null);
        lb2.setForeground(Color.white);
        lb2.setBackground(Color.white);
        lb2.setFont(new Font("宋体", Font.BOLD, 14));
        button2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        //设备管理
        JButton button3=new JButton();
        button3.setBounds(20, 220, 50, 50);
        ImageIcon logo3=new ImageIcon("src/main/resources/jc.jpg");
        button3.setIcon(logo3);
        frame.add(button3);
        button3.setBackground(Color.white);
        JLabel lb3=new JLabel("设备");
        lb3.setBounds(30,272,100,20);
        frame.add(lb3);
        frame.setLayout(null);
        lb3.setForeground(Color.white);
        lb3.setBackground(Color.white);
        lb3.setFont(new Font("宋体", Font.BOLD, 14));
        button3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        //进程管理
        JButton button4=new JButton();
        button4.setBounds(20, 320, 50, 50);
        ImageIcon logo4=new ImageIcon("src/main/resources/sb.jpg");
        button4.setIcon(logo4);
        frame.add(button4);
        button4.setBackground(Color.white);
        JLabel lb4=new JLabel("进程");
        lb4.setBounds(30,372,100,20);
        frame.add(lb4);
        frame.setLayout(null);
        lb4.setForeground(Color.white);
        lb4.setBackground(Color.white);
        lb4.setFont(new Font("宋体", Font.BOLD, 14));
        button4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                   //  ProcessFrame processFrame = new ProcessFrame();
                    try {
                        ProcessFrame.test();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                    //}
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
}