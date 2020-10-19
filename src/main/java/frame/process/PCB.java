package frame.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 程序控制块
 * @author: whj
 * @create: 2020-10-13 23:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PCB {
    private String uuid;
    private Register register;
    private Integer state;
    private String reason;

}
