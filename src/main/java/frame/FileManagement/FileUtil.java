package frame.FileManagement;

import frame.processManagement.Util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author ：Vizzk
 * @description：文件命令
 * @date ：2020/10/18 19:56
 */
public class FileUtil {
    private byte[][] diskBuffer;
    private final int ROOT_DIR = 4;
    private final byte EXE_PROPERTY = 5;
    private final byte TXT_PROPERTY = 1;
    private final byte DIR_PROPERTY = 3;
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
     * @description: 依次检查blockIndex盘块上是否存在目录name，存在则返回name所指向盘块号，否则返回-1
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
                blockIndex = getBlock(path[i], DIR_PROPERTY, nextBlockIndex);
                nextBlockIndex = fileAllocationTable.getNextBlock(nextBlockIndex);
            }
            while(blockIndex == -1 && nextBlockIndex != 1);
            nextBlockIndex = blockIndex;
        }
        return nextBlockIndex;
    }
    /**
     * @author: Vizzk
     * @description: 找到文件所指向盘块
     * @param name 文件名
     * @param property 文件属性
     * @param dirBlock 文件所在目录块
     * @return int
     */
    public int findFile(byte[] name, byte property, int dirBlock) {
        int blockIndex;
        if(dirBlock == -1){
            blockIndex = -1;
            return blockIndex;
        }
        int itemIndex = getItem(name, property, dirBlock);
        if(itemIndex == -1){
            blockIndex = -1;
            return blockIndex;
        }
        blockIndex = Disk.byteToUnsigned(diskBuffer[dirBlock][itemIndex*8+5]);
        return blockIndex;
    }
    /**
     * @author: Vizzk
     * @description: 返回文件所指向盘块
     * @param path
     * @return int
     */
    public int getFile(String path){
        byte property = TXT_PROPERTY;
        if(path.contains(".e")){
            property = EXE_PROPERTY;
        }
        byte[][] bytePath = disk.formatPath(path);
        byte[][] fatherPath = Arrays.copyOf(bytePath,bytePath.length-1);
        int parentDirBlock = this.findDirectory(fatherPath);
        if(parentDirBlock>4){
            parentDirBlock = this.getContained(bytePath[bytePath.length-1], property, parentDirBlock);
        }
        int dirBlock = findFile(bytePath[bytePath.length-1], property, parentDirBlock);
        return dirBlock;
    }
    /**
     * @author: Vizzk
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
     * @description: 返回文件或目录在盘块中的位置,-1不存在
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
     * @description: 返回文件长度
     * @param path 文件路径
     * @return int
     */
    public int getFileLength(String path){
        byte property = TXT_PROPERTY;
        if(path.contains(".e")){
            property = EXE_PROPERTY;
        }
        byte[][] bytePath = disk.formatPath(path);
        byte[][] fatherPath = Arrays.copyOf(bytePath,bytePath.length-1);
        byte[] name = bytePath[bytePath.length-1];
        int fatherDirBlock = this.findDirectory(fatherPath);
        if(fatherDirBlock>4){
            fatherDirBlock = this.getContained(bytePath[bytePath.length-1], property, fatherDirBlock);
        }
        int itemIndex = getItem(name, property, fatherDirBlock);
        int low = Disk.byteToUnsigned(diskBuffer[fatherDirBlock][itemIndex*8+6]);
        int high = Disk.byteToUnsigned(diskBuffer[fatherDirBlock][itemIndex*8+7])<<8;
        return low + high;

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

    /**
     * @author: Vizzk
     * @description: 将content转换为字节数据，若是可执行文件则先编译
     * @param content
     * @param isExecutable
     * @return byte[]
     */
    public byte[] createByteContent(String content, boolean isExecutable){
        byte[] bytes;
        if(isExecutable){
            bytes = Util.getByteFile(content);
        }
        else{
            bytes = disk.stringToBytes(content);
        }
        return bytes;
    }
    /**
     * @author: Vizzk
     * @description: 写文件入磁盘
     * @param name 文件名字
     * @param content 文件内容（二进制）
     * @param property 文件属性，文本文件为1，可执行文件为5
     * @param dirBlock 文件目录所在盘块
     * @return void
     */
    public void writeFile(byte[] name, byte[] content, byte property, int dirBlock) throws Exception{
        String message;
        byte[] bytes = content;
        int length = bytes.length;
        int blockNum = length / 64 + ((length % 64)==0 ? 0:1);
        if(!fileAllocationTable.isEmptyBlockEnough(blockNum-1)){
            message = "空间不足";
            return;
        }
        //blockIndex是所在的目录盘块，itemIndex是在盘块中哪一片，headBLock是文件所在的第一块盘块
        int blockIndex = getContained(name, property, dirBlock);
        int itemIndex = getItem(name, property, blockIndex);
        int headBlock = Disk.byteToUnsigned(diskBuffer[blockIndex][itemIndex*8+5]);

        fileAllocationTable.assignBlocks(headBlock,blockNum-1);
        writeContent(bytes, headBlock);
        writeLength(length, blockIndex, itemIndex);

        disk.writeDisk();
        message = "写入成功";
        System.out.println(message);
    }
    /**
     * @author: Vizzk
     * @description: 创建可执行文件或文本文件
     * @param path 文件路径
     * @param content 文件内容
     * @return void
     */
    public void createFile(String path, String content) throws Exception{
        byte[][] bytePath = disk.formatPath(path);
        byte[] byteContent;
        byte property = TXT_PROPERTY;
        String message;
        if(path.contains(".e")){
            property = EXE_PROPERTY;
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
        }
        else{
            assignDirectorySpace(bytePath[bytePath.length-1], property, (byte)1, dirBlock);
            byteContent = createByteContent(content, true);
        }
        writeFile(bytePath[bytePath.length-1], byteContent, property, dirBlock);
    }
    /**
     * @author: Vizzk
     * @description: 删除文件
     * @param path 文件路径
     * @return void
     */
    public void deleteFile(String path) throws Exception{
        String message;
        byte property = TXT_PROPERTY;
        if(path.contains(".e")){
            property = EXE_PROPERTY;
        }
        byte[][] bytePath = disk.formatPath(path);
        byte[][] fatherPath = Arrays.copyOf(bytePath,bytePath.length-1);
        int parentDirBlock = this.findDirectory(fatherPath);
        if(parentDirBlock>4){
            parentDirBlock = this.getContained(bytePath[bytePath.length-1], property, parentDirBlock);
        }
        int dirBlock = findFile(bytePath[bytePath.length-1], property, parentDirBlock);

        if(dirBlock == -1){
            message = "路径错误";
            return;
        }
        int itemIndex = getItem(bytePath[bytePath.length-1], property, parentDirBlock);
        System.out.println(parentDirBlock+" "+dirBlock+" "+itemIndex);

        fileAllocationTable.freeBlocks(dirBlock);
        deleteItem(parentDirBlock, itemIndex);
        if(isBlockEmpty(parentDirBlock)){
            fileAllocationTable.linkBlock(parentDirBlock);
        }
        disk.writeDisk();
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
            assignDirectorySpace(bytePath[bytePath.length-1], DIR_PROPERTY, (byte)0, dirBlock);
        }
        disk.writeDisk();
        diskBuffer = disk.getDisk();
       /* for(byte[] l:diskBuffer){
            for(byte b:l){
                System.out.print(b+" ");
            }
            System.out.println();
        }*/
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
            parentDirBlock = this.getContained(bytePath[bytePath.length-1], DIR_PROPERTY, parentDirBlock);
        }
        int dirBlock = this.findDirectory(bytePath);
        if(dirBlock == -1){
            message = "路径错误";
            System.out.println(message);
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
    /**
     * @author: Vizzk
     * @description: 获取文件内容并返回字符串
     * @param path
     * @return java.lang.String
     */
    public String getFileContent(String path){
        String contentString = null;
        byte property = TXT_PROPERTY;
        if(path.contains(".e")){
            property = EXE_PROPERTY;
        }
        int blockIndex = getFile(path);
        int length = getFileLength(path);
        byte[] content = new byte[length];
        int blockNum = length / 64;
        int itemNum = length % 64;
        int i;
        for(i=0; i < blockNum; i++){
            System.arraycopy(diskBuffer[blockIndex],0,content,64*i,64);
            blockIndex = fileAllocationTable.getNextBlock(blockIndex);
        }
        if(itemNum > 0){
            System.arraycopy(diskBuffer[blockIndex],0,content,64*i,itemNum);
        }
        if(property == EXE_PROPERTY){
            contentString = Util.getStringFile(content);
        }
        else{
            contentString = disk.bytesToString(content);
        }
        return contentString;
    }

    /**
     * @author: Vizzk
     * @description: 复制文件
     * @param srcPath 原文件路径
     * @param destPath  目标路径
     * @return void
     */
    public void copyFile(String srcPath, String destPath) throws Exception{
        String content = getFileContent(srcPath);
        createFile(destPath,content);
    }
    /**
     * @author: Vizzk
     * @description: 返回文件的名字和后缀，若目录则无后缀，t为文本文件，e为可执行文件
     * @param file
     * @return java.lang.String
     */
    public String getFileName(byte[] file){
        String name;
        String suffix = "";
        byte[] bytename = Arrays.copyOf(file,3);
        name = disk.bytesToString(bytename);
        if(file[4] == EXE_PROPERTY){
            suffix = ".e";
        }
        if(file[4] == TXT_PROPERTY){
            suffix = ".t";
        }
        name = name + suffix;
        return name;
    }
    /**
     * @author: Vizzk
     * @description: 返回path路径下的所有目录和文件，root为根目录
     * @param path
     * @return java.util.ArrayList<java.lang.String>
     */
    public ArrayList<String> getDirectorys(String path){

        ArrayList<String> directorys = new ArrayList<String>();
        byte[] item = new byte[8];
        String name;
        int blockIndex;
        if(path.equals("root")){
            blockIndex = ROOT_DIR;
        }
        else{
            byte[][] bytePath = disk.formatPath(path);
            blockIndex = this.findDirectory(bytePath);
        }
        do{
            for(int i=0; i<8; i++){
                System.arraycopy(diskBuffer[blockIndex],i*8,item,0,8);
                if(!Arrays.equals(emptyItem,item)){
                    if(path != "root"){
                        name = path+ "/" + getFileName(item);
                    }
                    else{
                        name = "/" + getFileName(item);
                    }
                    directorys.add(name);
                }
            }
            blockIndex = fileAllocationTable.getNextBlock(blockIndex);
        }
        while(blockIndex != 1);
        return directorys;
    }
    /**
     * @author:
     * @description: 删除路径
     * @param path 文件路径
     * @return boolean
     */
    public boolean deleteAll(String path) throws Exception{
        System.out.println(path);
        ArrayList<String> list = getDirectorys(path);
        for(int i=0; i< list.size(); i++){
            if(list.get(i).contains(".e") || list.get(i).contains(".t")){
                deleteFile(path+"/"+list.get(i));
            }
            else{
                deleteAll(path+"/"+list.get(i));
            }
        }
        removeDirectory(path);
        return true;
    }

    public void getAllFile(String path, ArrayList<String> files){
        ArrayList<String> list = getDirectorys(path);
        String filename;
        for(int i=0; i< list.size(); i++){
            if(list.get(i).contains(".e") || list.get(i).contains(".t")){
                if(list.get(i).contains(".e")){
                    if(path.equals("root")){
                        filename = list.get(i);
                    }
                    else{
                        filename = list.get(i);
                    }
                    files.add(filename);
                }
            }
            else{
                if(path.equals("root")){
                    getAllFile(list.get(i),files);
                }
                else{
                    getAllFile(list.get(i),files);
                }
            }
        }
    }
    /**
     * @author:
     * @description: 获取所有可执行文件路径
     * @param
     * @return java.util.ArrayList<java.lang.String>
     */
    public ArrayList<String> getExeFiles() throws Exception{
        diskBuffer = disk.getDisk();
        ArrayList<String> files = new ArrayList<String>();
        getAllFile("root",files);
        return files;
    }

    public void getFileBlock(String path){
        //getBlock()
    }
}
