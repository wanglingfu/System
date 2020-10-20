package frame.FileManagement;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author ：Vizzk
 * @description：文件命令
 * @date ：2020/10/18 19:56
 */
public class FileUtil {
    private byte[][] diskBuffer;
    private final int ROOT_DIR = 4;
    public FileUtil() throws Exception {
        Disk disk = new Disk();
        diskBuffer = disk.getDisk();
    }

    /**
     * @author: Vizzk
     * @description: 字符串转换为字节数组
     * @param string
     * @return byte[]
     */
    public byte[] stringToBytes(String string){
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        return bytes;
    }

    /**
     * @author: Vizzk
     * @description: 字节数组转换为字符串
     * @param bytes
     * @return java.lang.String
     */
    public String bytesToString(byte[] bytes){
        String string = new String(bytes, Charset.forName("UTF-8"));
        return string;
    }

    /**
     * @author: Vizzk
     * @description: 将路径字符串格式化为字节型并返回一个二维字节数组,数组每行为文件或目录名字数据
     * @param path
     * @return byte[][]
     */
    public byte[][] formatPath(String path){
        String temp= path.split("\\.")[0];
        String[] directory;
        byte[][] bytePath;
        directory = temp.split("/");
        if(directory[0].equals("")){
            directory = Arrays.copyOfRange(directory,1,directory.length);
        }
        bytePath = new byte[directory.length][3];
        for(int i=0; i<directory.length; i++){
            byte[] bytes = stringToBytes(directory[i]);
            int destPos = 0;
            if(bytes.length!=3){
                destPos=3-bytes.length;
            }
            System.arraycopy(bytes,0, bytePath[i], destPos,bytes.length);
        }
        return bytePath;
    }

    /**
     * @author: Vizzk
     * @description: 根据路径找到文件或目录所在盘块，返回0则是不存在
     * @param path
     * @return int
     */
    private int findDirectory(byte[][] path){
        int index = ROOT_DIR;
        boolean flag = true;//flag = false为没找到
        byte[] dirName;
        for(int i=0; i<path.length && flag; i++) {
            flag = false;
            for (int j=0; j<8; j++) {
                //循环取值磁盘中的目录名比较
                dirName = Arrays.copyOfRange(diskBuffer[index], j, j+3);
                if (Arrays.equals(path[i], dirName) && diskBuffer[index][j+6] != 0) {
                    index = Disk.byteToUnsigned(diskBuffer[index][j+6]);
                    flag = true;
                    break;
                }
            }
        }
        if(!flag){
            index = 0;
        }
        return index;
    }

    private boolean isDirFull(int blockIndex){
        boolean isFull = false;
        return isFull;
    }

    private boolean isContained(int blockIndex){
        boolean isContained = false;
        return false;
    }

    public void createFile(String name){

    }

    public void deleteFile(){

    }

    public void makeDirectory(String path){
        byte[][] bytePath = this.formatPath(path);
        int dirBlock = this.findDirectory(Arrays.copyOf(bytePath,bytePath.length-1));
        if(!isDirFull(dirBlock) && !isContained(dirBlock)){
        }
        /*for(String s:directory){
            bytes = stringToBytes(s);
            System.out.println(Arrays.toString(bytes));
            bytesToString(bytes);
        }*/
    }
}
