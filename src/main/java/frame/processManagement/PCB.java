package frame.processManagement;

import java.util.UUID;

/**
 * @description: 程序控制块
 * @author: whj
 * @create: 2020-10-13 23:51
 **/
public class PCB {
    /**
     * 进程标识符
     */
    private String uuid;
    /**
     * 阻塞原因， 哪个设备
     */
    private String reason;
    /**
     * 阻塞时间
     */
    private Integer time;
    /**
     * 文件
     */
    private byte[] file;
    /**
     * 数据
     */
    private Integer AX;
    /**
     * 程序计数器
     */
    private Integer PC;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Integer getAX() {
        return AX;
    }

    public void setAX(Integer AX) {
        this.AX = AX;
    }

    public Integer getPC() {
        return PC;
    }

    public void setPC(Integer PC) {
        this.PC = PC;
    }

    public PCB(byte[] file){
        uuid = UUID.randomUUID().toString();
        reason = null;
        time = 0;
        this.file =file;
        AX = 0;
        PC = 0;
    }
    public PCB(String uuid){
        this.uuid = uuid;
        AX = 0;
    }

}
