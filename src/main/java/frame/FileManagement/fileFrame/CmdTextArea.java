package frame.FileManagement.fileFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class CmdTextArea extends JTextArea implements KeyListener,    //命令行界面
        CaretListener {

    private boolean permission =false;
    private static final long serialVersionUID = 1L;
    private StringBuffer textBuffer = new StringBuffer();
    private int currentDot = -1;
    private boolean isAllowedInputArea = false;
    private int currentKeyCode = 0;
    private boolean isConsume = false;

    public CmdTextArea() {
        super();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (isConsume) {
            e.consume();
            return;
        }
        if (currentKeyCode == KeyEvent.VK_ENTER) {
            String input = this.getText().substring(textBuffer.length(),
                    this.getText().length() - 1);
            textBuffer.append(input);
            textBuffer.append("\n");
            if(permission){
                if(input.equals("exit")){
                    changePermission();
                    this.append("(Exit successfully)\n");
                    this.append("Please Input \"cmd\" To Get Administrator Permissions >");
                }
                else if(input.startsWith("create")){
                    String path=input.substring(input.indexOf('/')+1,input.length());
                    //if(path.substring(last))
                }
                else if(input.startsWith("delete")){
                    String path=input.substring(input.indexOf('/')+1,input.length());

                }
                else if(input.startsWith("open")){
                    String path=input.substring(input.indexOf('/')+1,input.length());

                }
                else if(input.startsWith("copy")){
                    String path=input.substring(input.indexOf('/')+1,input.length());

                }
                else if(input.startsWith("makedir")){
                    String path=input.substring(input.indexOf('/')+1,input.length());

                }
                else if(input.startsWith("remove")){
                    String path=input.substring(input.indexOf('/')+1,input.length());

                }
                else{
                    this.append("Invalid instructions\n");
                    this.append("C:\\Users\\James>");
                }
            }
            else{
                if(input.equals("cmd")){
                    changePermission();
                    this.append("(You are allow to manage documents through command prompt now)\n");
                    this.append("C:\\Users\\James >");
                }
                else{
                    this.append("Invalid instructions\n");
                    this.append("Please Input \"cmd\" To Get Administrator Permissions >");
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentKeyCode = e.getKeyCode();
        isConsume = checkConsume(e) ? true : false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isConsume) {
            e.consume();
            return;
        }
    }

    private boolean checkConsume(KeyEvent e) {
        if (!isAllowedInputArea) {
            e.consume();
            return true;
        }
        if ((currentKeyCode == KeyEvent.VK_BACK_SPACE
                || currentKeyCode == KeyEvent.VK_ENTER
                || currentKeyCode == KeyEvent.VK_UP || currentKeyCode == KeyEvent.VK_LEFT)
                && currentDot == textBuffer.length()) {
            e.consume();
            return true;
        }
        return false;
    }
    @Override
    public void append(String message) {
        super.append(message);
        textBuffer.append(message);
    }
    @Override
    public void caretUpdate(CaretEvent e) {
        currentDot = e.getDot();
        isAllowedInputArea = currentDot < textBuffer.length() ? false : true; //光标要在最后才允许输入
    }

    public void changePermission(){
        permission=!permission;
    }

    private boolean getPath(String path){  //获取类似“ /abc/aaa”字符串
        //先删除前边空格，循环做一个算法判断是不是/abc/aaa的形式（不能同时多个//）
        path.replaceAll(" ","");
        for(int i=0;i<path.length();i++){
           // if(Character.isLetter(path.charAt(i))||path.charAt(i)=='/')

        }
        return false;
    }
}