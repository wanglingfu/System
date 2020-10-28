import frame.processManagement.Runnable.CPU;
import org.junit.Test;

/**
 * @description: 测试类
 * @author: whj
 * @create: 2020-10-13 23:04
 **/
public class test {
    @Test
    public void test(){
        Byte[] bytes = {-55};
        CPU cpu = new CPU(0,bytes);
        cpu.setPSW(0);
        cpu.run();
        System.out.println(cpu.getAX());
    }
    @Test
    public void test2(){
    }
}
