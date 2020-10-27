package frame.processManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 程序控制块
 * @author: whj
 * @create: 2020-10-13 23:51
 **/

public class PCB {
    private String uuid;//进程标识符
    private Register register;//寄存器
    private String reason;//阻塞原因， 哪个设备
    private int size;//进程大小

    public PCB() {
    }

    public PCB(String uuid, Register register, String reason, int size) {
        this.uuid = uuid;
        this.register = register;
        this.reason = reason;
        this.size = size;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
