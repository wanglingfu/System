package frame.FileManagement.fileFrame;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
public class MyTreeNode extends DefaultMutableTreeNode{
    private Icon icon;
    MyTreeNode(String s,Icon i){
        super(s);
        icon=i;
    }
    public Icon getIcon(){
        return icon;
    }
    public void setIcon(Icon i){
        icon=i;
    }
}
