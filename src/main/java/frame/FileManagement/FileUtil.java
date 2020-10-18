package frame.FileManagement;

/**
 * @author ：Vizzk
 * @description：文件命令
 * @date ：2020/10/18 19:56
 */
public class FileUtil {
    private byte[][] DISK;
    public FileUtil(byte[][] DISK){
        this.DISK = DISK;
    }

    public void creatFile(String FILE){

    }

    public void deleteFile(){

    }

    public void makeDirectory(String DIR){
        String[] DIRECTORY;
        DIRECTORY = DIR.split("/");

        for(String s:DIRECTORY){
            System.out.println(s);
        }
    }
}
