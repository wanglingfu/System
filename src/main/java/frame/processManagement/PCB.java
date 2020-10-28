package frame.processManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 程序控制块
 * @author: whj
 * @create: 2020-10-13 23:51
 **/
@Data
@AllArgsConstructor
public class PCB {
    private String uuid;//进程标识符
    private Register register;//寄存器
    private String reason;//阻塞原因， 哪个设备
    private int time;
    private Byte[] file;//文件
    public PCB(Byte[] file){
        uuid=Util.getUUid();
        register=new Register();
        reason=null;
        time=0;
        this.file =file;
    }
}
