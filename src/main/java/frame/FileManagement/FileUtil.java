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
    private FAT fileAllocationTable;
    private Disk disk;
    public FileUtil() throws Exception {
        disk = new Disk();
        diskBuffer = disk.getDisk();
        fileAllocationTable = new FAT(disk);
    }

    /**
     * @author: Vizzk
     * @description: 依次检查blockIndex盘块上是否存在目录name，存在则返回name所在盘块号，否则返回-1
     * @param name
     * @param blockIndex
     * @param property
     * @return int
     */
    public int getBlock(byte[] name,byte property, int blockIndex){
        int block = -1;
        byte[] dirName;
        for (int j=0; j<8; j++) {
            //循环取值磁盘中的目录名比较
            int i = j * 8;
            dirName = Arrays.copyOfRange(diskBuffer[blockIndex], i, i+3);
            if (Arrays.equals(name, dirName) && diskBuffer[blockIndex][i+4] == property) {
                block = Disk.byteToUnsigned(diskBuffer[blockIndex][i+5]);
                break;
            }
        }
        return block;
    }

    /**
     * @author: Vizzk
     * @description: 根据路径在目录中找到该目录所指向的盘块，返回-1则是不存在
     * @param path
     * @return int
     */
    public int findDirectory(byte[][] path){
        int blockIndex = ROOT_DIR;
        int nextBlockIndex = blockIndex;
        for(int i=0; i<path.length && blockIndex != -1; i++) {
            do{
                blockIndex = getBlock(path[i], (byte)3, blockIndex);
                nextBlockIndex = fileAllocationTable.getNextBlock(nextBlockIndex);
            }
            while(blockIndex == -1 && nextBlockIndex != 1);
            nextBlockIndex = blockIndex;
        }
        return nextBlockIndex;
    }
    /**
     * @author: Vizzk
     * @description: 检查某个目录下是否包含某个文件，包含则返回文件所在的块序号，返回-1不存在，
     * @param name
     * @param property
     * @param blockIndex
     * @return int
     */
    public int getContained(byte[] name, byte property, int blockIndex){
        boolean isContained;
        int index = blockIndex;
        int itemIndex;
        int currentIndex;
        do{
            itemIndex = getBlock(name, property, index);
            currentIndex = index;
            index = fileAllocationTable.getNextBlock(index);
        }
        while(index > 1 && itemIndex == -1);
        isContained = (itemIndex != -1);
        if (!isContained){
            currentIndex = -1;
        }
        return currentIndex;
    }
    /**
     * @author: Vizzk
     * @description: 返回文件或目录在盘块中的位置
     * @param name
     * @param property
     * @param blockIndex
     * @return int
     */
    private int getItem(byte[] name, byte property, int blockIndex){
        int itemIndex = -1;
        byte[] dirName;
        for (int j=0, i=0; j<8; j++) {
            //循环取值磁盘中的目录名比较
            i = j * 8;
            dirName = Arrays.copyOfRange(diskBuffer[blockIndex], i,i+3);
            if (Arrays.equals(name, dirName) && diskBuffer[blockIndex][i+4] == property) {
                itemIndex = j;
                break;
            }
        }
        return itemIndex;
    }

    /**
     * @author: Vizzk
     * @description: 判断盘块及该盘块的下一盘块是否空间已满
     * @param blockIndex
     * @return boolean
     */
    public boolean isDirFull(int blockIndex){
        boolean isFull = true;
        byte[] emptyBlock = new byte[3];
        if(blockIndex == 4){
            if(getBlock(emptyBlock, (byte)0, blockIndex) != -1){
                isFull = false;
            }
        }
        else{
            if(getContained(emptyBlock, (byte)0, blockIndex)!=-1){
                isFull = false;
            }
            else{
                for(int i = 0; i < 4 && isFull; i++){
                    for(int j = 0; j < 64; j++){
                        if(diskBuffer[i][j] == 0){
                            isFull = false;
                            break;
                        }
                    }
                }
            }
        }
        return isFull;
    }
    /**
     * @author: Vizzk
     * @description: 给文件或目录分配空间,先寻找包含空闲空间的盘块，而后寻找空闲的目录项，再分配
     * @param name  文件名或目录名
     * @param property 文件或目录属性
     * @param isExecutable 是否为可执行文件
     * @param dirBlock  要分配的文件所在目录的盘块号
     * @return void
     */
    public void assignSpace(byte[] name, byte property, byte isExecutable, int dirBlock){
        int blockIndex = 0;
        int itemIndex;
        int assignedBlock;
        byte[] freeSpace = new byte[3];
        blockIndex = getContained(freeSpace, (byte)0, dirBlock);
        itemIndex = getItem(freeSpace, (byte)0, blockIndex);
        assignedBlock = fileAllocationTable.assignBlock();
        byte[] byteFile = new byte[8];
        System.arraycopy(name,0, byteFile, 0, 3);
        byteFile[3] = isExecutable;
        byteFile[4] = property;
        byteFile[5] = (byte)assignedBlock;
        System.arraycopy(byteFile, 0, diskBuffer[blockIndex], itemIndex*8, 8);
    }

    public void createFile(String name){

    }

    public void deleteFile(){

    }
    /**
     * @author: Vizzk
     * @description: 创建一个空目录
     * @param path 目录路径
     * @return void
     */
    public void makeDirectory(String path) throws Exception{
        byte[][] bytePath = disk.formatPath(path);
        int dirBlock = this.findDirectory(Arrays.copyOf(bytePath,bytePath.length-1));
        if(dirBlock == -1){
            System.out.println("路径错误！");
            return ;
        }

        if(!isDirFull(dirBlock) && getContained(bytePath[bytePath.length-1], (byte)3, dirBlock) == -1){
            assignSpace(bytePath[bytePath.length-1], (byte)3, (byte)0, dirBlock);
        }
        disk.writeDisk();
        System.out.println("创建成功");
        return;
        /*for(String s:bytePath){，
            bytes = stringToBytes(s);
            System.out.println(Arrays.toString(bytes));
            bytesToString(bytes);
        }*/
    }

    public void removeDirectory(String path){
        byte[][] bytePath = disk.formatPath(path);
        int dirBlock = this.findDirectory(Arrays.copyOf(bytePath,bytePath.length-1));
        if(dirBlock == -1){
            System.out.println("路径错误！");
            return ;
        }
    }
}
