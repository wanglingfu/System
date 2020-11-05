
import frame.processManagement.PCB;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @description: 测试类
 * @author: whj
 * @create: 2020-10-13 23:04
 **/
public class test {
    @Test
    public void Test(){
        Queue<PCB> pcbs = new LinkedList<PCB>();
        pcbs.add(new PCB(new Byte[10]));
        PCB pcb = pcbs.remove();
        System.out.println(pcb);
    }

    @Test
    public void test2(){}{

    }


}
