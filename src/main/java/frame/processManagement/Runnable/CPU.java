package frame.processManagement.Runnable;


import frame.processManagement.PCB;
import frame.processManagement.ProcessScheduling;

/**
 * @description: 执行进程
 * @author: whj
 * @create: 2020-10-19 22:30
 **/
public class CPU implements Runnable{
    private Integer AX;//数据寄存器
    private Integer PSW;//中断标志寄存器
    private String IR;//指令寄存器
    private Integer PC;//程序计数器
    private Byte[] file;//运行中的进程文件
    private int flag ;//是否运行完
    private int finalAX;//运行结束之后显示进程的AX

    private ProcessScheduling processScheduling;

    public CPU(Integer PC, Byte[] file,ProcessScheduling processScheduling) {
        this.PC = PC;
        this.file = file;
        this.processScheduling = processScheduling;
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

    public Integer getPC() {
        return PC;
    }

    public void setPC(Integer PC) {
        this.PC = PC;
    }

    public Byte[] getFile() {
        return file;
    }

    public void setFile(Byte[] file) {
        this.file = file;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 恢复主要寄存器
     */
    public void recovery(PCB pcb){
        AX = pcb.getAX();
        PC = pcb.getPC();
        file = pcb.getFile();
    }

    /**
     * 保存寄存器到CPU中
     * @param pcb
     * @return
     */
    public PCB preservation(PCB pcb){
        pcb.setAX(AX);
        pcb.setPC(PC);
        return pcb;
    }
    @Override
    public void run() {
        /**
         * 编码规则：
         * x++:00000000
         * x—:00100000
         * !A?:01000xxx
         * !B?:01001xxx
         * !C?:01010xxx
         * end:01100000
         * x=?:1xxxxxxx
         */
       while(flag>0){
            /**
             * 判断中断
             */
            switch(PSW){
                /**
                 * 程序停止
                 */
                case 1:{
                    PSW = 0;
                    processScheduling.destroy(processScheduling.getRunPCB());
                    finalAX = processScheduling.getRunPCB().getAX();
                    processScheduling.setRunPCB(processScheduling.getReadyPCB().remove());
                    recovery(processScheduling.getRunPCB());
                    break;
                }
                /**
                 * 时间片结束
                 */
                case 2:{
                    PSW = 0;
                    processScheduling.setRunPCB(preservation(processScheduling.getRunPCB()));
                    processScheduling.getReadyPCB().add(processScheduling.getRunPCB());
                    processScheduling.setRunPCB(processScheduling.getReadyPCB().remove());
                    if(processScheduling.getRunPCB() == null){
                        processScheduling.setRunPCB(processScheduling.getIdlePCB());
                    }
                    recovery(processScheduling.getRunPCB());
                    break;
                }
                case 3:{
                    PSW = 0;
                    break;
                }
            }
            int i = file[PC];
            int code = i/32; //前三位
            int time = i%8;
            //x++指令
            if (i>0&&code == 0){
                AX++;
            }
            //x--指令
            else if (i>0&&code == 1){
                AX--;
            }
            //！？？指令
            else if (i>0&&code == 2){
                PSW = 3;
            }
            //end指令
            else if (i>0&&code == 3){
                PSW = 1;
            }
            //x=?指令
            else {
                AX = i + 128;
            }
        }

    }
}
