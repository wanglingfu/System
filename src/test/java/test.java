import frame.DeviceManagement.DeviceA;
import frame.processManagement.Runnable.TimeSchedul;
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


}
