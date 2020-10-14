package frame.Process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: A
 * @author: whj
 * @create: 2020-10-13 23:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PCB {
    private int id;
    private String string;

    @Override
    public String toString() {
        return "PCB{" +
                "id=" + id +
                ", string='" + string + '\'' +
                '}';
    }
}
