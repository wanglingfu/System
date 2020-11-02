import frame.processManagement.Runnable.CPU;
import frame.processManagement.Runnable.CreatProcess;
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

    public static void main(String[] args) {
        Byte[][] bytes ={{1,1,1},{2,2,2}};
        CreatProcess creatProcess = new CreatProcess(bytes);
        new Thread(creatProcess).run();
        System.out.println(creatProcess.getFile(1));
    }
}
