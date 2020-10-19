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
    private Integer AX;
    private Integer PSW;
    private String IR;
    private String PC;
}
