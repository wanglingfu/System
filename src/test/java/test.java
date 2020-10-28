import com.sun.beans.editors.ByteEditor;
import frame.processManagement.PCB;
import frame.processManagement.Register;
import frame.processManagement.Runnable.CPU;
import frame.processManagement.Util;
import org.junit.Test;

/**
 * @description: 测试类
 * @author: whj
 * @create: 2020-10-13 23:04
 **/
public class test {
    @Test
    public void test(){
        Byte[] bytes = new Byte[10];
        CPU.runPCB=new PCB("1",new Register(1,1,"1",1),"1",1,bytes);
        byte b= (byte) ;
        Util.compiler(b);
        System.out.println(CPU.runPCB.getRegister().getAX());
    }
    @Test
    public void test2(){
    }
}
