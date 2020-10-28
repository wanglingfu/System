package frame.processManagement;

import frame.processManagement.Runnable.CPU;

import java.util.UUID;

/**
 * @description: 识别指令编译器
 * @author: whj
 * @create: 2020-10-16 10:54
 **/
public class Util {

    /**
     * 编译指令
     * @param ir
     */
    public static void compiler(Byte ir){
        /**
         * 编码规则：
         * x++:00000000
         * x—:00100000
         * !A?:01000xxx
         * !B?:01001xxx
         * !C?:01010xxx
         * end:01100000
         * x=?:1xxxxxxx
         */
        int i = ir.intValue();
        int code = i/32; //前三位
        int time = i%8;
        //x++指令
        if (i>0&&code == 0){
            Integer ax = CPU.runPCB.getRegister().getAX();
            CPU.runPCB.getRegister().setAX(ax+1);
            CPU.runPCB.getRegister().setIR("x++");
        }
        //x--指令
        else if (i>0&&code == 1){
            Integer ax = CPU.runPCB.getRegister().getAX();
            CPU.runPCB.getRegister().setAX(ax-1);
            CPU.runPCB.getRegister().setIR("x--");
        }
        //！？？指令
        else if (i>0&&code == 2){
            CPU.runPCB.getRegister().setPSW(3);
            CPU.runPCB.setTime(time);
            if(code/4==0){
                CPU.runPCB.setReason("A");
            }
            else if(code/4==1){
                CPU.runPCB.setReason("B");
            }
            else if(code/4==2){
                CPU.runPCB.setReason("C");
            }
            CPU.runPCB.getRegister().setIR("!"+ CPU.runPCB.getReason()+ CPU.runPCB.getTime());
        }
        //end指令
        else if (i>0&&code == 3){
            CPU.runPCB.getRegister().setPSW(1);
            CPU.runPCB.getRegister().setIR("end");
        }
        //x=?指令
        else {
            int j=ir+128;
            CPU.runPCB.getRegister().setIR("x="+""+j);
            CPU.runPCB.getRegister().setAX(ir+128);
        }
    }

    /**
     * 获取uuid
     * @return
     */
    public static String getUUid(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
