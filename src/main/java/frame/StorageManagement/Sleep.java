package frame.StorageManagement;

/**
 * @ClassName Sleep
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/18 12:51
 * @Version 1.0
 **/
public class Sleep {
    public void Sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
