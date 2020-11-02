import frame.deviceManagement.DeviceA;
import frame.storageManagement.Memory;
import frame.storageManagement.Sleep;
import org.junit.Test;

/**
 * @description: 测试类
 * @author: whj
 * @create: 2020-10-13 23:04
 **/
public class test {
    @Test
    public void Test(){
        DeviceA deviceA =new DeviceA();
        deviceA.getDevice("1",5,10);
        deviceA.getDevice("2",5,10);
        deviceA.getDevice("3",5,10);
        deviceA.getDevice("4",5,10);
        System.out.println(deviceA.getDeviceTable().getA1());
        System.out.println(deviceA.getDeviceTable().getA2());
        //System.out.println(deviceA.getBlock().get(1));
        deviceA.removeDevice("2");
        if (deviceA.getBlock() != null) {
            deviceA.gerFirstNode();
            System.out.println(deviceA.getDeviceTable().getA2());
        }
        deviceA.removeDevice("1");
        if (deviceA.getBlock() != null) {
            deviceA.gerFirstNode();
            System.out.println(deviceA.getDeviceTable().getA1());
        }
    }

    @Test
    public void test2(){}{
        Memory memory = new Memory(512);
        memory.BestFit(memory,10,"1");
        Sleep sleep = new Sleep();
        sleep.Sleep();
        memory.BestFit(memory,10,"2");
        sleep.Sleep();
        memory.test(memory);
        sleep.Sleep();
        memory.releaseMemory("2");
        sleep.Sleep();
        memory.BestFit(memory,200,"23");
        memory.test(memory);
    }


}
