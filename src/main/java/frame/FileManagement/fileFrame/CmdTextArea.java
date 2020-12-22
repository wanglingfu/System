package frame.FileManagement.fileFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import frame.FileManagement.*;

public class CmdTextArea extends JTextArea implements KeyListener,    //命令行界面
        CaretListener {

    private String work=null;
    private String path;
    private boolean permission =false;
    private static final long serialVersionUID = 1L;
    private StringBuffer textBuffer = new StringBuffer();
    private int currentDot = -1;
    private boolean isAllowedInputArea = false;
    private int currentKeyCode = 0;
    private boolean isConsume = false;
    private FileUtil fileUtil=new FileUtil();

    public CmdTextArea() throws Exception {
        super();
    }

    public void emptyWork(){
        work=null;
    }

    public String getWork(){
        return work;
    }

    public int getCurrentKeyCode(){
        return currentKeyCode;
    }

    public String getPathString(){
        return path;
    }

    @Override
    public void keyTyped(KeyEvent e){
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
                    work=null;
                    changePermission();
                    this.append("(Exit successfully)\n");
                    this.append("Please Input \"cmd\" To Get Administrator Permissions >");
                }
                else if(input.startsWith("create")){
                    String path=getPath(input);
                    if(path!=null){  //路径中不能有超过3个字符的
                        String []splits=path.split("/",-1);
                        for(String s:splits){
                            if(s.length()>3){
                                path=null;
                            }
                        }
                    }
                    if(path==null){ //格式错误
                        work=null;
                        this.append("Invalid instructions\n");
                        this.append("C:\\Users\\James>");
                    }
                    else{
                        work="create";
                        this.path=path;
                    }
                }
                else if(input.startsWith("delete")){
                    String path=getPath(input);
                    if(path==null){ //格式错误
                        work=null;
                        this.append("Invalid instructions\n");
                        this.append("C:\\Users\\James>");
                    }
                    else{
                        work="delete";
                        this.path=path;
                    }
                }
                else if(input.startsWith("open")){
                    String path=getPath(input);
                    if(path==null){ //格式错误
                        work=null;
                        this.append("Invalid instructions\n");
                        this.append("C:\\Users\\James>");
                    }
                    else{
                        work="open";
                        this.path=path;
                    }
                }
                else if(input.startsWith("copy")){
                    String path=getPath(input);
                    if(path==null){ //格式错误
                        work=null;
                        this.append("Invalid instructions\n");
                        this.append("C:\\Users\\James>");
                    }
                    else{
                        work="copy";
                        this.path=path;
                    }
                }
                else if(input.startsWith("makedir")){
                    String path=getPath(input);
                    if(path==null){ //格式错误
                        work=null;
                        this.append("Invalid instructions\n");
                        this.append("C:\\Users\\James>");
                    }
                    else{
                        work="makedir";
                        this.path=path;
                    }
                }
                else if(input.startsWith("remove")){
                    String path=getPath(input);
                    if(path==null){ //格式错误
                        work=null;
                        this.append("Invalid instructions\n");
                        this.append("C:\\Users\\James>");
                    }
                    else{
                        work="remove";
                        this.path=path;
                    }
                }
                else{
                    work=null;
                    this.append("Invalid instructions\n");
                    this.append("C:\\Users\\James>");
                }
            }
            else{
                work=null;
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


    public static String getPath(String withBlankPath){  //格式正确返回路径，错误返回null
        String str1=withBlankPath.substring(0,withBlankPath.lastIndexOf(" "));
        String path=withBlankPath.substring(str1.length()+1,withBlankPath.length());
        System.out.println(path);
        char[] chars = path.toCharArray();
        if (chars.length == 0){
            return null;
        }
        if (chars[0]!='/'){
            return null;
        }
        for (int i = 1; i < chars.length; i++) {
            if(isLetter(chars[i])){
                continue;
            }else if(chars[i] == '/'){
                if(i+1 < chars.length && isLetter(chars[i+1])){
                    i++;
                    continue;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
        return path;
    }
    public static boolean isLetter(char a){
        if(a >= 'A' && a <= 'Z' || a >= 'a' && a <= 'z'){
            return true;
        }
        return false;
    }
}