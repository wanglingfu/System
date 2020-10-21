package frame.processManagement;

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
     * @param pcb
     */
    public static void compiler(PCB pcb){

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
