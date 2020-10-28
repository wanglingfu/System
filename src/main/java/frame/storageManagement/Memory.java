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
        holes.add(new Hole(0, size));
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
    public Memory getMemory(int size,int location,String id,Hole hole) {  //size为申请大小 location为分配分区的下标
                                                                // hole为location对应的分区
        if (hole.getSize() - size >= MIN_SIZE) {
            Hole newHole = new Hole(hole.getHead() + size, hole.getSize() - size);
            holes.add(location + 1, newHole);
            hole.setSize(size);
        }
        //添加一个进程
        //id 进程名字
        pcbs.add(new ProcessAddress(id, hole));
        hole.setFree(false);
        System.out.println("成功分配大小为" + size + "的内存");
        return this;
    }
    /**
     *
     * @param memory
     * @param size
     * @param id
     * @Description  最佳适应算法
     * @return
     */
    public Memory BestFit(Memory memory,int size,String id){
        int findIndex = -1;  //最佳分区的下标
        int min = memory.getSize();
        for (int i = 0; i < memory.getHoles().size();i++){
            Hole hole = memory.getHoles().get(i);
            if(hole.isFree() && hole.getSize()>=size){
                if(min >hole.getSize() - size){
                    min = hole.getSize();
                    findIndex = i ;
                }
            }
        }
        if (findIndex != -1) {  //若存在合适分区
            return memory.getMemory(size, findIndex, id,memory.getHoles().get(findIndex));
        }
        System.err.println("OUT OF MEMORY!");
        return memory;
    }

    /**
     *
     * @param id
     * @return
     */
    // 内存回收
    public Memory releaseMemory(String id){
        ProcessAddress pcb = null;
            boolean flag = false;
            for(int i =0; i < pcbs.size(); i++){
                if(pcbs.get(i).getId().equals(id) == true){
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
            if(hole.isFree()){
                System.out.println("此空间空闲，无需释放：\t" + id);
                return  this;
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
            return this;
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
