package frame.FileManagement;

import frame.processManagement.Util;

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
    private byte[] emptyBlock;
    private byte[] emptyItem;
    public FileUtil() throws Exception {
        disk = new Disk();
        diskBuffer = disk.getDisk();
        fileAllocationTable = new FAT(disk);
        emptyBlock = new byte[64];
        emptyItem = new byte[8];
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
                blockIndex = getBlock(path[i], (byte)3, nextBlockIndex);
                nextBlockIndex = fileAllocationTable.getNextBlock(nextBlockIndex);
            }
            while(blockIndex == -1 && nextBlockIndex != 1);
            nextBlockIndex = blockIndex;
        }
        return nextBlockIndex;
    }
    /**
     * @author: Vizzk
     * @description: 检查某个目录下是否包含某个文件，包含则返回文件所在目录的块序号，返回-1不存在，
     * @description: 检查某个目录下是否包含某个文件，包含则返回文件所在目录的块序号，返回-1不存在，
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
    public void deleteItem(int blockIndex, int itemIndex){
        System.arraycopy(emptyItem,0,diskBuffer[blockIndex],itemIndex*8,8);
    }
    /**
     * @author: Vizzk
     * @description: 检查盘块是否为空闲
     * @param blockIndex
     * @return boolean
     */
    public boolean isBlockEmpty(int blockIndex){
        boolean isEmpty = Arrays.equals(emptyBlock, diskBuffer[blockIndex]);
        return isEmpty;
    }

    /**
     * @author: Vizzk
     * @description: 判断目录否空间已满
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
            //有空目录项，查看是否有空间可分配
            if(getContained(emptyBlock, (byte)0, blockIndex)!=-1){
                isFull = !fileAllocationTable.isEmptyBlockEnough(1);
            }
            else{
               isFull = !fileAllocationTable.isEmptyBlockEnough(2);
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
    public void assignDirectorySpace(byte[] name, byte property, byte isExecutable, int dirBlock){
        int blockIndex = 0;
        int itemIndex;
        int assignedBlock;
        byte[] freeSpace = new byte[3];
        blockIndex = getContained(freeSpace, (byte)0, dirBlock);
        if(blockIndex == -1){
            blockIndex = fileAllocationTable.assignNextBlock(dirBlock);
        }
        itemIndex = getItem(freeSpace, (byte)0, blockIndex);
        assignedBlock = fileAllocationTable.assignBlock();
        byte[] byteFile = new byte[8];
        System.arraycopy(name,0, byteFile, 0, 3);
        byteFile[3] = isExecutable;
        byteFile[4] = property;
        byteFile[5] = (byte)assignedBlock;
        System.arraycopy(byteFile, 0, diskBuffer[blockIndex], itemIndex*8, 8);
    }
    /**
     * @author: Vizzk
     * @description: 将文件长度写入磁盘中，第6位是低位
     * @param length 长度
     * @param blockIndex 目录盘块号
     * @param itemIndex 目录所在盘块位置
     * @return void
     */
    public void writeLength(int length, int blockIndex, int itemIndex){

        byte[] temp = Disk.lengthToBytes(length);
        diskBuffer[blockIndex][itemIndex*8+6] = temp[0];
        diskBuffer[blockIndex][itemIndex*8+7] = temp[1];
    }
    /**
     * @author: Vizzk
     * @description: 将内容写入到以headblock为开头盘块的磁盘中
     * @param content
     * @param headBlock
     * @return void
     */
    public void writeContent(byte[] content, int headBlock){
        int blockNum = content.length / 64;
        int tailNum = content.length % 64;
        int blockIndex = headBlock;
        for(int i = 0; i < blockNum; i++){
            System.arraycopy(content, i*64, diskBuffer[blockIndex], 0, 64);
            blockIndex = fileAllocationTable.getNextBlock(blockIndex);
        }
        if(tailNum > 0){
            System.arraycopy(content, blockNum*64, diskBuffer[blockIndex], 0, tailNum);
        }
    }

    public byte[] createByteContent(String content, boolean isExecutable){
        byte[] bytes = null;
        if(isExecutable){
            //bytes = Util.getFile(content);
        }
        else{
            bytes = disk.stringToBytes(content);
        }
        return bytes;
    }
    public void createTextFile(byte[] name, byte[] content, int dirBlock) throws Exception{
        String message;
        byte[] bytes = content;
        int length = bytes.length;
        int blockNum = length / 64 + ((length % 64)==0 ? 0:1);
        if(!fileAllocationTable.isEmptyBlockEnough(blockNum-1)){
            message = "空间不足";
            return;
        }
        //blockIndex是所在的目录盘块，itemIndex是在盘块中哪一片，headBLock是文件所在的第一块盘块
        int blockIndex = getContained(name, (byte)1, dirBlock);
        int itemIndex = getItem(name, (byte)1, blockIndex);
        int headBlock = Disk.byteToUnsigned(diskBuffer[blockIndex][itemIndex*8+5]);

        fileAllocationTable.assignBlocks(headBlock,blockNum-1);
        writeContent(bytes, headBlock);
        writeLength(length, blockIndex, itemIndex);

        disk.writeDisk();
        message = "写入成功";
        System.out.println(message);
    }
    public void createFile(String path, String content) throws Exception{
        byte[][] bytePath = disk.formatPath(path);
        byte[] byteContent;
        byte property;
        String message;
        if(path.contains(".e")){
            property = (byte)5;
        }
        else{
            property = (byte)1;
        }
        int dirBlock = this.findDirectory(Arrays.copyOf(bytePath,bytePath.length-1));
        if(dirBlock == -1){
            message = "路径错误！";
            System.out.println(message);
            return ;
        }
        if(isDirFull(dirBlock)){
            message = "磁盘已满";
            System.out.println(message);
            return;
        }
        if(getContained(bytePath[bytePath.length-1], property, dirBlock) != -1){
            message = "该路径下有同名文件";
            System.out.println(message);
            return;
        }
        if(property == 1){
            assignDirectorySpace(bytePath[bytePath.length-1], property, (byte)0, dirBlock);
            byteContent = createByteContent(content, false);
            createTextFile(bytePath[bytePath.length-1], byteContent, dirBlock);
        }
        else{
            assignDirectorySpace(bytePath[bytePath.length-1], property, (byte)1, dirBlock);
            byteContent = createByteContent(content, true);
            //createExecutableFile(content);
        }
        //disk.writeDisk();
    }

    public void deleteFile(String path){
        String message;
        byte[][] bytePath = disk.formatPath(path);
        byte[][] fatherPath = Arrays.copyOf(bytePath,bytePath.length-1);
        int parentDirBlock = this.findDirectory(fatherPath);
        if(parentDirBlock>4){
            parentDirBlock = this.getContained(bytePath[bytePath.length-1], (byte)3, parentDirBlock);
        }
        int dirBlock = this.findDirectory(bytePath);
        if(dirBlock == -1){
            message = "路径错误";
            return;
        }

        int itemIndex = getItem(bytePath[bytePath.length-1], (byte)3, parentDirBlock);
        fileAllocationTable.freeBlock(dirBlock);
        deleteItem(parentDirBlock, itemIndex);
        if(isBlockEmpty(parentDirBlock)){
            fileAllocationTable.linkBlock(parentDirBlock);
        }
        //disk.writeDisk();
        System.out.println("delete success");
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
            assignDirectorySpace(bytePath[bytePath.length-1], (byte)3, (byte)0, dirBlock);
        }
        disk.writeDisk();
        System.out.println("创建成功");
        return;

    }
    /**
     * @author: Vizzk
     * @description: 从硬盘中移除空目录
     * @param path
     * @return void
     */
    public void removeDirectory(String path) throws Exception{
        String message;
        byte[][] bytePath = disk.formatPath(path);
        byte[][] fatherPath = Arrays.copyOf(bytePath,bytePath.length-1);
        int parentDirBlock = this.findDirectory(fatherPath);
        if(parentDirBlock>4){
            parentDirBlock = this.getContained(bytePath[bytePath.length-1], (byte)3, parentDirBlock);
        }
        int dirBlock = this.findDirectory(bytePath);
        if(dirBlock == -1){
            message = "路径错误";
            return;
        }
        if(!isBlockEmpty(dirBlock)){
            message = "目录非空";
            return;
        }
        int itemIndex = getItem(bytePath[bytePath.length-1], (byte)3, parentDirBlock);
        fileAllocationTable.freeBlock(dirBlock);
        deleteItem(parentDirBlock, itemIndex);
        if(isBlockEmpty(parentDirBlock)){
            fileAllocationTable.linkBlock(parentDirBlock);
        }
        disk.writeDisk();
        System.out.println("delete success");
    }
}
