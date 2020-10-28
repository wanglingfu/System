package frame.storageManagement;

/**
 * @ClassName ProcessAddress
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/17 10:21
 * @Version 1.0
 **/
public class ProcessAddress {
    private String id;
    private Hole hole;

    public  ProcessAddress(String id,Hole hole){
        this.id=id;
        this.hole=hole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Hole getHole() {
        return hole;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }
}
