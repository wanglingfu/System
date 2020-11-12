package frame.storageManagement;

/**
 * @ClassName Hole
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/16 21:49
 * @Version 1.0
 **/
public class Hole {
    private String uid;
    private  int head;  //内存块的起始地址
    private  int size;  //内存块的大小
    private  boolean isFree;  //内存块的空闲状态

    public Hole(int head,int size){
            this.head=head;
            this.size=size;
            this.isFree=true;

    }

    public Hole(String uid, int head, int size) {
        this.uid = uid;
        this.head = head;
        this.size = size;
        this.isFree = true;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
