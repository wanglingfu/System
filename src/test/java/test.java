
import frame.processManagement.PCB;
import frame.processManagement.Util;
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
    /**
     * 编码规则：
     * x++:00000000
     * x—:00100000
     * !A?:01000xxx
     * !B?:01001xxx
     * !C?:01010xxx
     * end:01100000
     * x=?:1xxxxxxx
     * 特殊：
     * !A8:00001000
     * !A9:00001001
     * !B8:00011000
     * !B9:00011001
     * !C8:00101000
     * !C9:00101001
     */
    @Test
    public void Test(){
        String IR = null;
        int i = Util.compile("x=0");
        int code = i/64;//是否为特殊
        int[] device = {(i % 64) / 16, (i % 64) / 8};
        int[] time = {i%16,i%8};
        //x++指令
        if (i==0){
            IR = "x++";
        }
        //x--指令
        else if (i==32){
            IR = "x--";
        }
        //end指令
        else if (i == 96){
            IR = "end";
        }

        //x=?指令
        else if (i<0){
            i = i + 128;
            IR = "x="+i;
        }
        //！？？指令
        else {
            if(device[code] == 0){
                IR = "!A"+time[code];
            }
            if(device[code] == 1){
                IR = "!B"+time[code];
            }
            if(device[code] == 2){
                IR = "!C"+time[code];
            }
        }
        System.out.println(IR);
    }

    @Test
    public void test2(){}{

    }


}
