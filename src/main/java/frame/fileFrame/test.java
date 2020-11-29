package frame.fileFrame;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;


public class test {

    public test() {
        jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.pack();
        jf.setVisible(true);
        surface(1500, 800, jf);
    }

    private JFrame jf;
    private JPanel contentPane;
    private JMenuBar menu;
    private JButton button1;
    private JButton button2;
    private JPanel catalog;
    private JPanel commandLine;
    private JPanel view;
    private JPanel right;
    private JPanel bottom;
    private JPanel left;
    private myButton[][] buttons;
    private JPopupMenu listPopupMenu;
    private JPopupMenu filePopupMenu;
    private JTree tree;
    private DefaultMutableTreeNode selectionNode;
    private TreePath movePath;

    private void initial() { //初始化操作
        contentPane = new JPanel();
        //contentPane.setPreferredSize(new Dimension(width,height));
        contentPane.setLayout(new BorderLayout());
        menu = new JMenuBar();
        button1 = new JButton("回退");
        button2 = new JButton("前进");
        //GridLayout layout=new GridLayout(16,4,20,15);
        view = new JPanel(new GridLayout(32, 8, 15, 10));
        catalog = new JPanel();
        right = new JPanel();
        bottom = new JPanel();
        left = new JPanel();
        buttons = new myButton[32][8];
    }

    private void surface(int width, int height, JFrame jf) {
        initial();
        jf.setContentPane(contentPane);
        setView();
        toolBar(30);
        //设置右边间隙
        contentPane.add(right, BorderLayout.EAST);
        right.setPreferredSize(new Dimension(10, 0));
        //设置底部间隙
        contentPane.add(bottom, BorderLayout.SOUTH);
        bottom.setPreferredSize(new Dimension(0, 10));
        //设置左边间隙
        contentPane.add(left, BorderLayout.WEST);
        left.setPreferredSize(new Dimension(10, 0));
        jf.setSize(width, height);
        jf.setLocation(0, 0);
    }

    private JComponent view() { //磁盘盘块视图
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new myButton("NO." + (i * 8 + j),
                        new ImageIcon(getClass().getResource("/images/被选中盘块.png")), i * 8 + j);
                //buttons[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/images/被选中盘块.png")));  //设置被占用盘块的图标
                //buttons[i][j].setEnabled(false);  //不可用表示盘块被占用
                buttons[i][j].setBackground(new Color(227, 223, 226));
                view.add(buttons[i][j]);
            }
        }
        view = getBorderPane(view, 40, 15, 15, 15); //让盘块与边界有间隔，看起来舒服些
        JPanel description = new JPanel(new FlowLayout(FlowLayout.LEFT));
        description.add(new JLabel("  说明:"));
        description.add(new JLabel(new ImageIcon(getClass().getResource("/images/未占用盘块mini.png"))));
        description.add(new JLabel("未占用盘块     "));
        description.add(new JLabel(new ImageIcon(getClass().getResource("/images/被占用盘块mini.png"))));
        description.add(new JLabel("被占用盘块     "));
        description.add(new JLabel(new ImageIcon(getClass().getResource("/images/被选中盘块mini.png"))));
        description.add(new JLabel("当前目录/文件所占用的盘块 "));
        view.add(description, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setViewportView(view);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50); //滑轮滚动量
        //contentPane.add(scrollPane,BorderLayout.CENTER);
        return scrollPane;
    }

    private JComponent command(){
        commandLine=new JPanel();
        return commandLine;
    }

    private JComponent catalog() { //目录视图
        JPanel catalogPanel = new JPanel(new BorderLayout());
        JLabel description = new JLabel("文件目录");
        catalogPanel.add(description, BorderLayout.NORTH);
        description.setPreferredSize(new Dimension(0,20));
        menuItemProcessing();  //菜单项
        DefaultMutableTreeNode rootNode = getRootNode("中国");
        selectionNode = null;// 使用根节点创建树组件
        tree = new JTree(rootNode);// 设置树显示根节点句柄
        tree.setShowsRootHandles(false); // 设置树节点可编辑
        tree.setEditable(false); // 设置节点选中监听器
        //tree.addMouseListener(mouseListener);  //结点拖动
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                System.out.println("当前被选中的节点: " + e.getPath());
            }
        });
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //左键单击目录项则选中，更新选中节点，单机其他地方选中节点为空
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (tree.getPathForLocation(e.getX(), e.getY()) != null) {
                        selectionNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                    } else {
                        selectionNode = null;
                        tree.setSelectionPath(null);
                    }
                }
                //鼠标右键点击选中节点，则打开菜单项
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (tree.getPathForLocation(e.getX(), e.getY()) != null
                            && tree.getPathForLocation(e.getX(), e.getY()) == tree.getSelectionPath()) {
                        //右键打开菜单项,判断是文件还是目录
                        if (isFile(selectionNode))
                            filePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                        else listPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    } else {
                        selectionNode = null;
                        tree.setSelectionPath(null);
                    }
                }
                if(tree.getSelectionPath()!=null)
                selectionNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
            }
            @Override
            public void mouseReleased(MouseEvent e) {   //保证了选中结点的正确
                selectionNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
            }
        });
        JScrollPane scrollPane = new JScrollPane(tree);
        catalogPanel.add(scrollPane, BorderLayout.CENTER);
        return catalogPanel;
    }

    private void setView() {
        JSplitPane splitViewCommand = new JSplitPane();
        JSplitPane splitPane = new JSplitPane();
        splitViewCommand.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitViewCommand.setTopComponent(view());
        splitViewCommand.setBottomComponent(command());
        splitPane.setLeftComponent(catalog());
        splitPane.setRightComponent(splitViewCommand);
        splitPane.setOneTouchExpandable(true);
        splitViewCommand.setOneTouchExpandable(true);
        // 拖动分隔条时连续重绘组件
        splitViewCommand.setContinuousLayout(true);
        splitPane.setContinuousLayout(true);
        // 设置分隔条的初始位置
        splitViewCommand.setDividerLocation(500);
        splitPane.setDividerLocation(150);
        contentPane.add(splitPane, BorderLayout.CENTER);
    }

    private void menuItemProcessing() { //菜单项设置
        listPopupMenu = new JPopupMenu();
        filePopupMenu = new JPopupMenu();
        listPopupMenu.add(createMenuItem("新建目录", "新建目录"));
        listPopupMenu.add(createMenuItem("新建文件", "新建文件"));
        listPopupMenu.add(createMenuItem("删除", "删除目录"));
        listPopupMenu.add(createMenuItem("属性", "目录属性"));
        filePopupMenu.add(createMenuItem("打开", "打开文件"));
        filePopupMenu.add(createMenuItem("删除", "删除文件"));
        filePopupMenu.add(createMenuItem("属性", "文件属性"));
    }

    private boolean isFile(DefaultMutableTreeNode node) { //判断是目录还是文件
        if(node==null) return false;
        if(node.getAllowsChildren())   //如果允许有孩子结点，则为目录，否则是文件
            return false;
        else return true;
    }

    private JMenuItem createMenuItem(String text, String action) {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(action);
        item.addActionListener(actionListener);
        return item;
    }

    /*private MouseListener mouseListener=new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            //如果需要唯一确定某个节点，必须通过TreePath来获取。
            TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
            if (tp != null)
            {
                movePath = tp;
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            //根据鼠标松开时的TreePath来获取TreePath
            TreePath tp = tree.getPathForLocation(e.getX(), e.getY());

            if (tp != null && movePath != null)
            {
                //阻止向子节点拖动
                if (movePath.isDescendant(tp) && movePath != tp)
                {
                    JOptionPane.showMessageDialog(jf, "目标节点是被移动节点的子节点，无法移动！", "非法操作", JOptionPane.ERROR_MESSAGE );
                    return;
                }
                //既不是向子节点移动，而且鼠标按下、松开的不是同一个节点
                else if (movePath != tp)
                {
                    System.out.println(tp.getLastPathComponent());
                    //add方法可以先将原节点从原父节点删除，再添加到新父节点中
                    ((DefaultMutableTreeNode)tp.getLastPathComponent()).add((DefaultMutableTreeNode)movePath.getLastPathComponent());
                    movePath = null;
                    tree.updateUI();
                }
            }
        }
    };*/

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("新建目录"))
                addItem("新目录");
            else if (action.equals("新建文件"))
                addItem("新文件");
             else if (action.equals("删除目录")) {
                deleteItem();
                System.out.println("删除了"+getPathString(selectionNode));
            } else if (action.equals("目录属性")) {
                System.out.println(getPathString(selectionNode));
            } else if (action.equals("打开文件")) {
                showTxtFile(jf, contentPane);
            } else if (action.equals("删除文件")) {
                System.out.println("删除了"+getPathString(selectionNode));
                deleteItem();
            } else if (action.equals("文件属性")) {
                System.out.println(getPathString(selectionNode));
            }
        }
    };

    private void showTxtFile(Frame owner, Component parentComponent) {
        // txt文件显示
        final JDialog dialog = new JDialog(owner, getPathString(selectionNode), true);
        JTextField txtField = new JTextField("ss");
        JPanel txtPanel = getBorderPane(txtField, 40, 40, 40, 40);
        JPanel topPanel = new JPanel(null);
        JPanel bottomPanel = new JPanel(null);
        JButton saveButton = new JButton("保存");
        JButton cancleButton = new JButton("取消");
        JLabel label = new JLabel("文件内容");
        label.setBounds(10, 5, 80, 20);
        saveButton.setBounds(300, 10, 80, 30);
        cancleButton.setBounds(420, 10, 80, 30);
        bottomPanel.add(saveButton);
        bottomPanel.add(cancleButton);
        topPanel.add(label);
        bottomPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setPreferredSize(new Dimension(0, 30));
        txtPanel.add(bottomPanel, BorderLayout.SOUTH);
        txtPanel.add(topPanel, BorderLayout.NORTH);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.setSize(800, 600);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentComponent);
        // 设置对话框的内容面板
        dialog.setContentPane(txtPanel);
        // 显示对话框
        dialog.setVisible(true);
    }

    public String getPathString(DefaultMutableTreeNode node) { //根据节点以字符串形式返回路径
        String path = "";
        String previous = "";
        if(node==null)
            return null;
        while (node.getParent() != null) {
            previous = path;
            path = '/' + node.getUserObject().toString() + previous;
            node = (DefaultMutableTreeNode) node.getParent();
        }
        previous = path;
        path = '/' + node.getUserObject().toString() + previous;
        return path;
    }


    private DefaultMutableTreeNode getRootNode(String root) {  //通过根目录路径构建路径树，然后返回该树的根结点
        Queue<String> stringQueue=new LinkedList<String>();
        Queue<DefaultMutableTreeNode> nodeQueue=new LinkedList<DefaultMutableTreeNode>();
        stringQueue.offer(root);
        DefaultMutableTreeNode nowNode;
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        nodeQueue.offer(rootNode);
        nowNode=rootNode;
        while(!stringQueue.isEmpty()){
            String node=stringQueue.poll();
            nowNode= nodeQueue.element();
            nodeQueue.remove();
            String childNodes[]=returnChilds(node);
            if(childNodes!=null){
                for(String s:childNodes){
                    stringQueue.offer(s);
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(s);
                    nodeQueue.offer(childNode);
                    nowNode.add(childNode);
                }
            }
        }
        return rootNode;
    }


    private void toolBar(int height) { //工具栏视图
        menu.setPreferredSize(new Dimension(0, height));
        contentPane.add(menu, BorderLayout.NORTH);
        button1.setBackground(new Color(204, 204, 204));
        menu.add(button1);
        button2.setBackground(new Color(204, 204, 204));
        menu.add(button2);
    }

    public JPanel getBorderPane(JComponent p, int northBorder, int southBorder, int westBorder, int eastBorder) { //返回一个能指定边界间隔大小的JPanel
        JPanel panel = new JPanel(new BorderLayout());
        JPanel east = new JPanel();
        JPanel west = new JPanel();
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        panel.add(p, BorderLayout.CENTER);
        panel.add(east, BorderLayout.EAST);
        panel.add(west, BorderLayout.WEST);
        panel.add(south, BorderLayout.SOUTH);
        panel.add(north, BorderLayout.NORTH);
        east.setPreferredSize(new Dimension(eastBorder, 0));
        south.setPreferredSize(new Dimension(0, southBorder));
        north.setPreferredSize(new Dimension(0, northBorder));
        west.setPreferredSize(new Dimension(westBorder, 0));
        return panel;
    }

    private String[] returnChilds(String s) {
        if (s == "中国")
            return new String[]{"广东", "福建"};
        else if (s == "广东")
            return new String[]{"广州", "深圳"};
        else if (s == "福建")
            return new String[]{"泉州", "厦门"};
        else if(s=="深圳"){
            return new String[]{"龙岗"};
        }
        else return null;
    }


    private void deleteItem(){   //删除文件或目录
        if(selectionNode==null)
            return ;
        DefaultMutableTreeNode node=selectionNode;
        node.removeFromParent();
        tree.updateUI();
        selectionNode=null;
    }

    private void addItem(String newNodeString){   //新建文件
        if(selectionNode==null)
            return ;
        DefaultMutableTreeNode newNode=new DefaultMutableTreeNode(newNodeString);
        if(newNode.toString()=="新文件")    //如果建立的是文件则不允许在其有孩子结点
            newNode.setAllowsChildren(false);
        selectionNode.add(newNode);
        TreeNode[] nodes = newNode.getPath();   //自动展开新建的目录
        TreePath path = new TreePath(nodes);
        tree.scrollPathToVisible(path);
        tree.updateUI();
        selectionNode=null;
    }
}
