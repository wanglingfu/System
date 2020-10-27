package frame.processManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 寄存器
 * @author: whj
 * @create: 2020-10-16 11:06
 **/

public class Register {
    private Integer AX;//数据
    private Integer PSW;//程序状态，中断状态，1、程序结束 2、时间片结束 3、I/O中断
    private String IR;//指令寄存器
    private int PC;//程序计数器

    public Register(Integer AX, Integer PSW, String IR, int PC) {
        this.AX = AX;
        this.PSW = PSW;
        this.IR = IR;
        this.PC = PC;
    }

    public Integer getAX() {
        return AX;
    }

    public void setAX(Integer AX) {
        this.AX = AX;
    }

    public Integer getPSW() {
        return PSW;
    }

    public void setPSW(Integer PSW) {
        this.PSW = PSW;
    }

    public String getIR() {
        return IR;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }
}
