package frame.FileManagement.fileFrame;
import frame.FileManagement.*;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
//字体设置 label1.setFont(new Font("宋体",Font.BOLD,16));


public class ViewInitialization {

    public ViewInitialization() throws Exception {
        jf = new JFrame("文件资源管理器");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.pack();
        jf.setVisible(true);
        surface(1800, 1000, jf);
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
    private JPopupMenu listPopupMenu;
    private JPopupMenu filePopupMenu;
    private JTree tree;
    private DefaultMutableTreeNode selectionNode;
    private TreePath movePath;
    private FileUtil fileUtil;
    private JButton buttons[];

    private void initial() throws Exception { //初始化操作
        fileUtil=new FileUtil();
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        menu = new JMenuBar();
        button1 = new JButton("回退");
        button2 = new JButton("前进");
        view = new JPanel(new GridLayout(32, 8, 15, 10));
        catalog = new JPanel();
        right = new JPanel();
        bottom = new JPanel();
        left = new JPanel();
    }

    private void surface(int width, int height, JFrame jf) throws Exception {
        initial();
        jf.setContentPane(contentPane);
        setView();
        //toolBar(30);
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
        buttons=new JButton[256];
        for(int i=0;i<256;i++) buttons[i]=new JButton("NO." + (i),new ImageIcon("src/main/resources/未占用盘块mini.png"));
        updateImage();
        for(int i=0;i<256;i++)   view.add(buttons[i]);
        view = getBorderPane(view, 40, 15, 15, 15); //让盘块与边界有间隔，看起来舒服些
        JPanel description = new JPanel(new FlowLayout(FlowLayout.LEFT));
        description.add(new JLabel("  说明:"));
        description.add(new JLabel(new ImageIcon("src/main/resources/未占用盘块mini.png")));
        description.add(new JLabel("未占用盘块     "));
        description.add(new JLabel(new ImageIcon("src/main/resources/被占用盘块mini.png")));
        description.add(new JLabel("被占用盘块     "));
        description.add(new JLabel(new ImageIcon("src/main/resources/被选中盘块mini.png")));
        description.add(new JLabel("当前目录/文件所占用的盘块 "));
        view.add(description, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setViewportView(view);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50); //滑轮滚动量
        //contentPane.add(scrollPane,BorderLayout.CENTER);
        return scrollPane;
    }

    private void updateImage() {   //更新按钮信息
        int buttonsAttribute[] = fileUtil.getFAT();
        for(int a:buttonsAttribute)
        //被占用磁盘块是1，被当前目录项占用是2，空闲为0
        if(getPathString(selectionNode)==""){
            buttonsAttribute[4] = 2;
        }
        else if (selectionNode != null) {
            System.out.println(getPathString(selectionNode));
            ArrayList<Integer> nowOccupied = fileUtil.getFileBlock(getPathString(selectionNode));
            for (int i : nowOccupied) {
                buttonsAttribute[i] = 2;
            }
        }
        //for(int i=0;i<256;i++) System.out.print(buttonsAttribute[i]+" ");
        for (int i = 0; i < 256; i++) {
            if (buttonsAttribute[i] == 0) {
                buttons[i].setIcon(new ImageIcon("src/main/resources/未占用盘块.png"));
                buttons[i].setText("NO." + (i));
            } else if (buttonsAttribute[i] == 2) {  //2表示当前目录项占用的
                buttons[i].setIcon(new ImageIcon("src/main/resources/被选中盘块.png"));
                buttons[i].setText("NO." + (i));
            } else {     //所有其他被占用的磁盘块
                buttons[i].setIcon(new ImageIcon("src/main/resources/被占用盘块.png"));
                buttons[i].setText("NO." + (i));
            }
            buttons[i].setBackground(new Color(227, 223, 226));
        }
    }


    private JComponent command(){
        commandLine=new JPanel(new BorderLayout());
        frame.FileManagement.fileFrame.CmdTextArea cmdText=new frame.FileManagement.fileFrame.CmdTextArea();
        cmdText.addKeyListener(cmdText);
        cmdText.addCaretListener(cmdText);
        cmdText.setFont(new Font("宋体", Font.PLAIN, 20));
        cmdText.append("Please Input \"cmd\" To Get Administrator Permissions >");
        cmdText.requestFocus();
        cmdText.setCaretPosition(cmdText.getText().length());
        commandLine.add(cmdText,BorderLayout.CENTER);
        return commandLine;
    }

    private JComponent catalog() throws Exception { //目录视图
        JPanel catalogPanel = new JPanel(new BorderLayout());
        JLabel description = new JLabel("文件目录");
        catalogPanel.add(description, BorderLayout.NORTH);
        description.setPreferredSize(new Dimension(0,20));
        menuItemProcessing();  //菜单项
        DefaultMutableTreeNode rootNode = getRootNode("root");
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
                    updateImage();   //更新磁盘界面
                    view.updateUI();
                    if (tree.getPathForLocation(e.getX(), e.getY()) != null) {
                        selectionNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                    }else {
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
                if(tree.getSelectionPath()!=null)
                    selectionNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
            }
        });
        JScrollPane scrollPane = new JScrollPane(tree);
        catalogPanel.add(scrollPane, BorderLayout.CENTER);
        return catalogPanel;
    }

    public String getPathString(DefaultMutableTreeNode node) { //根据节点以字符串形式返回路径,如果是根路径返回空串
        String path = "";
        String previous = "";
        if(node==null)
            return null;
        while (node.getParent() != null) {
            previous = path;
            path = '/' + node.getUserObject().toString() + previous;
            node = (DefaultMutableTreeNode) node.getParent();
        }
        //System.out.println(path);
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
            //String[] childNodes=returnChilds(node);
            if((!node.contains(".txt"))&&(!node.contains(".exe"))){   //不是文件的路径才执行下一步
                ArrayList<String>childNodes=fileUtil.getDirectorys(node);
                if(childNodes.size()!=0){
                    for(String path:childNodes){
                        stringQueue.offer(path);
                        String s=path.substring(path.lastIndexOf("/")+1);
                        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(s);
                        if((s.contains(".txt"))||(s.contains(".exe"))) //如果是文件结点，不允许其有孩子
                            childNode.setAllowsChildren(false);
                        nodeQueue.offer(childNode);
                        nowNode.add(childNode);
                    }
                }
            }
        }
        return rootNode;
    }

    private void menuItemProcessing() { //菜单项设置
        listPopupMenu = new JPopupMenu();
        filePopupMenu = new JPopupMenu();
        listPopupMenu.add(createMenuItem("新建目录", "新建目录"));
        listPopupMenu.add(createMenuItem("新建文件", "新建文件"));
        listPopupMenu.add(createMenuItem("删除", "删除"));
        listPopupMenu.add(createMenuItem("属性", "目录属性"));
        filePopupMenu.add(createMenuItem("打开", "打开文件"));
        filePopupMenu.add(createMenuItem("删除", "删除"));
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

    // ArrayList<String> getDirectorys(String path)
    // boolean makeDirectory(String path)  //1路径错误 2磁盘已满 3同名文件
    // boolean createFile(String path, String content)
    // void removeDirectory(String path)  //删除空目录
    // void deleteFile(String path)
    // void deleteAll(String path)
    // String getFileContent(String path)
    // int getFileLength(String path)
    //public boolean copyFile(String srcPath, String destPath) 复制文件
    //public ArrayList<Integer> getFileBlock(String path) 返回目录项占用的盘块
    //int[] getFAT() 被占用盘块
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("新建目录")) {
                showDirectoryCreation();
            }
            else if (action.equals("新建文件")) {
                try {
                    showFileCreation(jf, contentPane);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            else if (action.equals("删除")) {
                try {
                    deleteItem();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (action.equals("目录属性")) {
                System.out.println(getPathString(selectionNode));
            } else if (action.equals("打开文件")) {
                showTxtFile(jf, contentPane);
            } else if (action.equals("文件属性")) {
                System.out.println(getPathString(selectionNode));
            }
        }
    };

    private void showDirectoryCreation(){
        try {
            String inputContent = JOptionPane.showInputDialog(jf,
                    "新建目录命名：(要求小于三个字符)\n",
                    "新建项命名",3);
            int flag=1;
            for(int i=0;i<inputContent.length();i++){
                if(!Character.isUpperCase(inputContent.charAt(i))&&!Character.isLowerCase(inputContent.charAt(i))){
                    JOptionPane.showMessageDialog(jf, "目录名称有误！", "提示", JOptionPane.WARNING_MESSAGE);
                    flag=0;
                    break;
                }
            }
            if(flag==1){
                if(inputContent.length()==0)
                    JOptionPane.showMessageDialog(jf, "目录名称不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                else if(inputContent.length()>3)
                    JOptionPane.showMessageDialog(jf, "目录名称不能大于3个字符！", "提示", JOptionPane.WARNING_MESSAGE);
                else {
                    int p = addDirectory(inputContent);
                    if(p==0){
                        updateImage();
                        view.updateUI();
                    }
                    else if (p == 2)
                        JOptionPane.showMessageDialog(jf, "磁盘已满，无法添加！", "提示", JOptionPane.WARNING_MESSAGE);
                    else if (p == 3)
                        JOptionPane.showMessageDialog(jf, "同级下有同名同类型文件，无法添加！", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private void showFileCreation(Frame owner, Component parentComponent) {
        //创建文件时弹出的命名窗口
        final JDialog dialog = new JDialog(owner,"新建文本命名", true);
        JPanel mainPanel =new JPanel(null);
        JRadioButton radioBtn01 = new JRadioButton("txt");
        JRadioButton radioBtn02 = new JRadioButton("exe");
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);
        radioBtn01.setSelected(true);
        JLabel label1 = new JLabel("名称:"); label1.setFont(new Font("宋体",Font.BOLD,16));
        JLabel label2 = new JLabel("内容:"); label2.setFont(new Font("宋体",Font.BOLD,16));
        JLabel label3 = new JLabel("属性:"); label3.setFont(new Font("宋体",Font.BOLD,16));
        JLabel label4 = new JLabel("(文本名称不大于3个字符)"); label4.setFont(new Font("黑体",Font.PLAIN,14));
        JTextArea textArea=new JTextArea("默认内容"); textArea.setFont(new Font("宋体",Font.BOLD,16));
        textArea.setLineWrap(true);  //自动换行
        JScrollPane scrollPane=new JScrollPane();
        JTextField nameField=new JTextField(); nameField.setFont(new Font("宋体",Font.BOLD,16));
        JButton saveButton = new JButton("创建");
        JButton cancleButton = new JButton("取消");
        label1.setBounds(30, 30, 50, 30);
        label2.setBounds(30, 130, 50, 30);
        label3.setBounds(30, 80, 50, 30);
        label4.setBounds(220, 30, 180, 30);
        radioBtn01.setBounds(80,80,50,30);
        radioBtn02.setBounds(170,80,50,30);
        nameField.setBounds(80,30,140,30);
        textArea.setBounds(80,130,220,100);
        saveButton.setBounds(70, 250, 70, 30);
        cancleButton.setBounds(270, 250, 70, 30);
        mainPanel.add(saveButton);
        mainPanel.add(cancleButton);
        mainPanel.add(label1);
        mainPanel.add(label2);
        mainPanel.add(label3);
        mainPanel.add(label4);
        mainPanel.add(radioBtn01);
        mainPanel.add(radioBtn02);
        mainPanel.add(nameField);
        mainPanel.add(textArea);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //创建
                String fileNameString=nameField.getText();
                String fileText=textArea.getText();
                int flag=1;
                for(int i=0;i<fileNameString.length();i++){
                    if(!Character.isUpperCase(fileNameString.charAt(i))&&!Character.isLowerCase(fileNameString.charAt(i))){
                        JOptionPane.showMessageDialog(jf, "目录名称有误！", "提示", JOptionPane.WARNING_MESSAGE);
                        flag=0;
                        break;
                    }
                }
                if(flag==1){
                    if(fileNameString.length()==0){
                        JOptionPane.showMessageDialog(jf, "文件名称不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                    else if(fileNameString.length()>3){
                        JOptionPane.showMessageDialog(jf, "文件名称不能超过3个字符", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        try {
                            if (radioBtn01.isSelected())
                                fileNameString = fileNameString + ".txt";
                            else fileNameString = fileNameString + ".exe";
                            int p = addFile(fileNameString, fileText);
                            if(p==0){
                                updateImage();
                                view.updateUI();
                                dialog.dispose();
                            }
                            else if (p == 2)
                                JOptionPane.showMessageDialog(jf, "磁盘已满，无法添加！", "提示", JOptionPane.WARNING_MESSAGE);
                            else if (p == 3)
                                JOptionPane.showMessageDialog(jf, "同级下有同名同类型文件，无法添加！", "提示", JOptionPane.WARNING_MESSAGE);
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });
        cancleButton.addActionListener(new ActionListener() {  //取消
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.setSize(400, 350);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentComponent);
        // 设置对话框的内容面板
        dialog.setContentPane(mainPanel);
        // 显示对话框
        dialog.setVisible(true);
    }

    private void setView() throws Exception {  //设置界面的分隔拖拉条
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
        splitViewCommand.setDividerLocation(600);
        splitPane.setDividerLocation(150);
        contentPane.add(splitPane, BorderLayout.CENTER);
    }


    private void showTxtFile(Frame owner, Component parentComponent) {
        // txt文件显示和修改
        String txtName=selectionNode.getUserObject().toString();
        final JDialog dialog = new JDialog(owner,txtName,true);
        String content= fileUtil.getFileContent(getPathString(selectionNode));
        JTextArea textArea = new JTextArea(content);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("宋体",Font.BOLD,24));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(textArea);

        JPanel txtPanel = getBorderPane(scrollPane, 100, 60, 70, 60);
        JPanel topPanel = new JPanel(null);
        JPanel bottomPanel = new JPanel(null);
        JButton saveButton = new JButton("保存");
        JButton cancleButton = new JButton("取消");
        JLabel label = new JLabel("文件内容");
        label.setFont(new Font("黑体",Font.PLAIN,16));
        label.setBounds(10, 5, 80, 20);
        saveButton.setBounds(300, 10, 80, 30);
        cancleButton.setBounds(420, 10, 80, 30);
        bottomPanel.add(saveButton);
        bottomPanel.add(cancleButton);
        topPanel.add(label);
        bottomPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setPreferredSize(new Dimension(0, 30));
        txtPanel.add(bottomPanel,BorderLayout.SOUTH);
        txtPanel.add(topPanel,BorderLayout.NORTH);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String newContent=textArea.getText();
                try {
                    fileUtil.deleteFile(getPathString(selectionNode));
                    fileUtil.createFile(getPathString(selectionNode),newContent);
                    updateImage();
                    view.updateUI();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                dialog.dispose();
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
        if (s == "root")
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


    private void deleteItem() throws Exception {   //删除文件或目录
        if(selectionNode==null)
            return ;
        //System.out.println(selectionNode.toString());
        if(selectionNode.toString().contains(".txt")||selectionNode.toString().contains(".exe"))
            fileUtil.deleteFile(getPathString(selectionNode));  //删除文件
        else fileUtil.deleteAll(getPathString(selectionNode));  //删除目录
        //System.out.println(getPathString(selectionNode));
        selectionNode.removeFromParent();
        tree.updateUI();
        selectionNode=null;
        updateImage();
        view.updateUI();
    }

    private int addDirectory(String newNodeString) throws Exception {   //新建目录
        System.out.println(getPathString(selectionNode)+'/'+newNodeString);
        int p=fileUtil.makeDirectory(getPathString(selectionNode)+'/'+newNodeString);
        if(p!=0)
            return p;
        DefaultMutableTreeNode newNode=new DefaultMutableTreeNode(newNodeString);
        newNode.setAllowsChildren(true);
        selectionNode.add(newNode);
        TreeNode[] nodes = newNode.getPath();   //自动展开新建的目录
        TreePath path = new TreePath(nodes);
        tree.scrollPathToVisible(path);
        tree.setSelectionPath(path);
        selectionNode=newNode;
        tree.updateUI();
        return 0;
    }

    private int addFile(String newNodeString,String content) throws Exception {   //新建文件
        int p=fileUtil.createFile(getPathString(selectionNode)+'/'+newNodeString,content);
        if(p!=0)
            return p;
        DefaultMutableTreeNode newNode=new DefaultMutableTreeNode(newNodeString);
        newNode.setAllowsChildren(false);   //不允许在文件下创建目录项
        selectionNode.add(newNode);
        TreeNode[] nodes = newNode.getPath();   //自动展开新建的目录
        TreePath path = new TreePath(nodes);
        tree.scrollPathToVisible(path);
        tree.setSelectionPath(path);
        selectionNode=newNode;
        tree.updateUI();
        return 0;
    }

}
