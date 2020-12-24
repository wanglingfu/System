package frame.storageManagement;

import java.util.LinkedList;

/**
 * @ClassName Memory
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/16 21:53
 * @Version 1.0
 **/
public class Memory {
    int id1;
    private  int size;  //内存块大小
    private  int lastfind;  //上次寻址结束的下标
    private LinkedList<ProcessAddress> pcbs;
    private LinkedList<Hole> holes;
    private  static  final  int MIN_SIZE = 0;

    public  Memory(int size){
        this.size = size;
        this.holes = new LinkedList<Hole>();
        this.pcbs = new LinkedList<ProcessAddress>();
        holes.add(new Hole(null,0, size));
    }
    //申请内存

    /**
     *
     * @param size
     * @param location
     * @param id
     * @param hole
     * @return
     */
    public void getMemory(int size,int location,String id,Hole hole) {  //size为申请大小 location为分配分区的下标
                                                                // hole为location对应的分区
        if (hole.getSize() - size >= MIN_SIZE) {
            Hole newHole = new Hole(null,hole.getHead() + size, hole.getSize() - size);
            holes.add(location + 1, newHole);
            hole.setSize(size);
            hole.setUid(id);
        }
        //添加一个进程
        //id 进程名字
        pcbs.add(new ProcessAddress(id, hole));
        hole.setFree(false);
        //System.out.println("成功分配大小为" + size + "的内存");
    }
    /**
     *
     * @param
     * @param size
     * @param id
     * @Description  最佳适应算法
     * @return
     */
    public boolean BestFit(int size,String id){
        int findIndex = -1;  //最佳分区的下标
        int min = this.getSize();
        for (int i = 0; i < this.getHoles().size();i++){
            Hole hole = this.getHoles().get(i);
            if(hole.isFree() && hole.getSize()>=size){
                if(min >hole.getSize() - size){
                    min = hole.getSize();
                    findIndex = i ;
                }
            }
        }
        if (findIndex != -1) {  //若存在合适分区
            this.getMemory(size, findIndex, id,this.getHoles().get(findIndex));
           return true;
        }
        else {
            System.err.println("OUT OF MEMORY!");
            return false;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    // 内存回收
    public void releaseMemory(String id){
        ProcessAddress pcb = null;
            boolean flag = false;
            for(int i =0; i < pcbs.size(); i++){
                if(id.equals(pcbs.get(i).getId())== true){
                    pcb = pcbs.get(i);
                    flag = true;
                    break;
                }
            }
        if(flag == false){
            System.out.println("无此分区："+id);
        }
        if (pcb != null) {
            for (int i = 0; i < holes.size(); i++) {
                Hole hole = holes.get(i);
                if ((pcb.getHole().getSize() == hole.getSize()) && (pcb.getHole().getHead() == hole.getHead())) {
                    id1 = i;
                    break;
                }
            }
        }
            Hole hole = holes.get(id1);
            hole.setUid(null);
            if(hole.isFree()){
                System.out.println("此空间空闲，无需释放：\t" + id);
            }
            for (int i = 0; i < pcbs.size(); i++){
                ProcessAddress pcb2 = pcbs.get(i);
                if((pcb2.getHole().getHead()==hole.getHead())&&(pcb2.getHole().getSize()==hole.getSize())){
                    pcbs.remove(i);
                    break;
                }
            }
            //判断后面分区是否为true，合并分区
            if (id1 < holes.size()-1&&holes.get(id1+1).isFree()){
                Hole nextHole = holes.get(id1 + 1);
                hole.setSize(hole.getSize()+nextHole.getSize());
                holes.remove(nextHole);
            }
            //判断前面分区是否为true，合并分区
            if (id1 >0 &&holes.get(id1-1).isFree()){
                Hole lastHole = holes.get(id1 - 1);
                lastHole.setSize(hole.getSize()+lastHole.getSize());
                holes.remove(id1);
                id1--;

        }
            holes.get(id1).setFree(true);
            System.out.println("回收内存成功");
    }
    //测试

    /**
     *
     * @param memory
     */
    public  void test(Memory memory){
        for (int i = 0;i < holes.size();i++){
            Hole h = memory.getHoles().get(i);
            System.out.println(" "+h.getHead()+" "+h.getSize()+" "+h.isFree());
        }
        for (int i = 0;i < pcbs.size();i++){
            ProcessAddress b = memory.getProcessAddress().get(i);
            Hole c = b.getHole();
            System.out.println(b.getId()+" "+c.getHead()+" "+c.getSize()+" "+c.isFree());
        }
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLastfind() {
        return lastfind;
    }

    public void setLastfind(int lastfind) {
        this.lastfind = lastfind;
    }

    public LinkedList<ProcessAddress> getProcessAddress() {
        return pcbs;
    }

    public void setProcessAddress(LinkedList<ProcessAddress> processAddress) {
        this.pcbs = processAddress;
    }

    public LinkedList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(LinkedList<Hole> holes) {
        this.holes = holes;
    }

    public static int getMinSize() {
        return MIN_SIZE;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "size=" + size +
                ", lastfind=" + lastfind +
                ", processAddress=" + pcbs +
                ", holes=" + holes +
                '}';
    }
}
