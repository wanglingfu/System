package Main;

import frame.FileManagement.FileUtil;
import frame.FileManagement.fileFrame.ViewInitialization;
import frame.deviceManagement.Device;
import frame.processManagement.PCB;
import frame.processManagement.ProcessScheduling;
import frame.processManagement.Runnable.CPU;
import frame.processManagement.Runnable.CreatProcess;
import frame.processManagement.Util;
import frame.storageManagement.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
public class main extends JFrame {

    private static final long serialVersionUID=1L;
    private static boolean bool = true;
    public main(){
        setResizable(false);
        setBounds(250,17,900,700);
        setTitle("Double-Z");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) throws Exception {
        JFrame frame = new main();
        /*String plaf="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try{
            UIManager.setLookAndFeel(plaf);
            SwingUtilities.updateComponentTreeUI(frame);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }*/
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

        JButton button4=new JButton();
        button4.setBounds(20, 150, 50, 50);
        ImageIcon logo4=new ImageIcon("src/main/resources/sb.jpg");
        button4.setIcon(logo4);
        frame.add(button4);
        button4.setBackground(Color.white);
        JLabel lb4=new JLabel("进程");
        lb4.setBounds(30,210,100,20);
        frame.add(lb4);
        frame.setLayout(null);
        lb4.setForeground(Color.white);
        lb4.setBackground(Color.white);
        lb4.setFont(new Font("宋体", Font.BOLD, 14));

        ProcessFrame processFrame = new ProcessFrame();
        ViewInitialization v  =new ViewInitialization();

        button1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        v.t();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
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

        button4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    if (bool == true || ProcessFrame.bool == 1) {
                        processFrame.setVisibleTest(processFrame);
                        try {
                            ProcessFrame.test(processFrame);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        bool = false;
                    }
                    else {
                        processFrame.setVisibleTest(processFrame);
                    }
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