package frame.processManagement;

import java.util.UUID;

/**
 * @description: 识别指令编译器
 * @author: whj
 * @create: 2020-10-16 10:54
 **/
public class Util {
    /**
     * 返回是哪个指令
     * @param IR
     * @return
     */
    public static int compare(String IR){
        char[] chars = IR.toCharArray();
        if(chars[1]=='='){
            return 1;
        } else if(chars[1]=='+'){
            return 2;
        } else if(chars[1]=='-'){
            return 3;
        } else if(chars[1]=='A'||chars[1]=='B'||chars[1]=='C'){
            return 4;
        }else {
            return 5;
        }
    }

    /**
     * 编译指令
     * @param IR
     * @param pcb
     */
    public static void compiler(String IR,PCB pcb){
        int compare = compare(IR);
        char[] chars = IR.toCharArray();
        switch(compare){
            case 1:{
                int num=0;
                for (int i = 2; i < chars.length; i++) {
                    num*=10;
                    num+=chars[i]-'0';
                }
                pcb.getRegister().setAX(num);
                break;
            }
            case 2: {
                pcb.getRegister().setAX(pcb.getRegister().getAX()+1);
                break;
            }
            case 3: {
                pcb.getRegister().setAX(pcb.getRegister().getAX()-1);
                break;
            }
            case 4: {

                break;
            }
            case 5: {

            }
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
