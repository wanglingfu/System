package frame.FileManagement;

import java.util.Arrays;

public class FAT {
    private byte[][] diskBuffer;
    public FAT(Disk disk) throws Exception {
        diskBuffer = disk.getDisk();
    }
    /**
     * @author: Vizzk
     * @description: 检测是否有num个空磁盘
     * @param num
     * @return boolean
     */
    protected boolean isEmptyBlockEnough(int num){
        boolean isEnough = false;
        int count = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 64; j++){
                if(diskBuffer[i][j] == 0){
                    count++;
                }
            }
        }
        if(count >= num){
            isEnough = true;
        }
        return isEnough;
    }
    /**
     * @author: Vizzk
     * @description: 返回blockIndex的下一块盘块号，返回0为传来的盘块未分配，1为没有下一块
     * @param blockIndex
     * @return int
     */
    protected int getNextBlock(int blockIndex){
        int nextIndex = 0;
        int i = blockIndex / 64;
        int j = blockIndex % 64;
        nextIndex = diskBuffer[i][j];
        return nextIndex;
    }

    public void assignBlocks(int headBlock, int num){
        int index = headBlock;
        for(int i=0; i<num; i++){
            index = assignNextBlock(index);
        }
    }
    /**
     * @author: Vizzk
     * @description: 查找文件分配表并分配空盘块，返回-1说明磁盘已满
     * @param
     * @return int
     */
    public int assignBlock(){
        int blockIndex = -1;
        boolean flag = false;
        for(int i=0; i<4 && !flag; i++){
            for(int j=0; j<64; j++){
                if(diskBuffer[i][j] == 0){
                    blockIndex = i*64 + j;
                    diskBuffer[i][j] = 1;
                    flag = true;
                    break;
                }
            }
        }
        return blockIndex;
    }
    /**
     * @author: Vizzk
     * @description: 给下标为blockIndex的盘块分配下一盘块空间，并返回下一盘块索引号
     * @param blockIndex
     * @return int
     */
    public int assignNextBlock(int blockIndex){
        int i = blockIndex / 64;
        int j = blockIndex % 64;
        int assignedIndex;
        assignedIndex = assignBlock();
        diskBuffer[i][j] = (byte)assignedIndex;
        return assignedIndex;
    }
    /**
     * @author: Vizzk
     * @description: 将该盘块文件分配表中的位置数据置零
     * @param blockIndex
     * @return void
     */
    public void freeBlock(int blockIndex){
        int i = blockIndex / 64;
        int j = blockIndex % 64;
        diskBuffer[i][j] = 0;
        return ;
    }
    /**
     * @author: Vizzk
     * @description: 将blockIndex所在目录盘块的上一盘块和下一盘块块连接
     * @param blockIndex
     * @return void
     */
    public void linkBlock(int blockIndex){
        int nextBlock = getNextBlock(blockIndex);
        boolean flag = true;
        for(int i=0; i < 4 && flag; i++){
            for(int j=0; j<64; j++){
                if(diskBuffer[i][j] == blockIndex){
                    diskBuffer[i][j] = (byte)nextBlock;
                    flag = false;
                    break;
                }
            }
        }
        freeBlock(blockIndex);
        return ;
    }
}
