package frame.processManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 寄存器
 * @author: whj
 * @create: 2020-10-16 11:06
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Register {
    private Integer AX;//数据
    private Integer PSW;//程序状态，中断状态，1、程序结束 2、时间片结束 3、I/O中断
    private String IR;//指令寄存器
    private int PC;//程序计数器
}
