import frame.Process.PCB;
import frame.ProcessFrame;
import frame.StorageManagement.Hole;
import frame.StorageManagement.Memory;
import frame.StorageManagement.Sleep;
import org.junit.Test;

/**
 * @description: 测试类
 * @author: whj
 * @create: 2020-10-13 23:04
 **/
public class test {
    @Test
    public void test1(){
        int location = 0;
      //  int id=0;
        Memory a = new Memory(512);
        Sleep  sleep = new Sleep();
        a.BestFit(a,50,"a");
        sleep.Sleep();
        a.BestFit(a,450,"b");
        sleep.Sleep();
        a.BestFit(a,12,"c");
        sleep.Sleep();
        a.test(a);
        a.releaseMemory("b");
        a.test(a);
        sleep.Sleep();
        a.BestFit(a,25,"d");
        a.test(a);
        a.releaseMemory("c");
        a.test(a);

    }


}
