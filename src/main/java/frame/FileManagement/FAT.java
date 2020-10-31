package frame.FileManagement;

import java.util.Arrays;

public class FAT {
    private byte[][] diskBuffer;
    public FAT(Disk disk) throws Exception {
        diskBuffer = disk.getDisk();
    }
    /**
     * @author: Vizzk
     * @description: 返回blockIndex的下一块盘块号，返回0为传来的盘块未分配，
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
}
