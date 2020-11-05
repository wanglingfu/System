package frame.processManagement.Runnable;


import Main.main;
import frame.deviceManagement.Device;
import frame.deviceManagement.DeviceTable;
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
    private int SystemTime = 0;//系统时间

    private ProcessScheduling processScheduling;
    private DeviceTable deviceTable;

    public CPU(int flag, Integer PC, Byte[] file, ProcessScheduling processScheduling, DeviceTable deviceTable) {
        this.flag = flag;
        this.deviceTable = deviceTable;
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
     * 保存寄存器到PCB中
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
       while(flag>0){
           /**
            * 一个系统时间执行一条指令
            */
           while(SystemTime == main.SystemTime) {}
           SystemTime ++;
            /**
             * 判断中断
             */
            switch(PSW){
                /**
                 * 程序停止
                 */
                case 1:{
                    PSW = 0;
                    finalAX = processScheduling.getRunPCB().getAX();
                    processScheduling.destroy();
                    recovery(processScheduling.getRunPCB());
                    flag--;
                    break;
                }
                /**
                 * 时间片结束
                 */
                case 2:{
                    PSW = 0;
                    processScheduling.setRunPCB(preservation(processScheduling.getRunPCB()));
                    processScheduling.util(2);
                    recovery(processScheduling.getRunPCB());
                    break;
                }
                /**
                 * 设备中断
                 */
                case 3:{
                    PSW = 0;
                    main.lockTime.lock();
                    /**
                     * 判断哪个设备已使用完
                     */
                    if(main.DeviceTime[0] ==0){
                        String a1 = deviceTable.getA1();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==1){
                        String a1 = deviceTable.getA2();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==2){
                        String a1 = deviceTable.getB1();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==3){
                        String a1 = deviceTable.getB2();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==4){
                        String a1 = deviceTable.getB3();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==5){
                        String a1 = deviceTable.getC1();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==6){
                        String a1 = deviceTable.getA2();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    else if(main.DeviceTime[0] ==7){
                        String a1 = deviceTable.getA2();
                        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
                            if(processScheduling.getBlockPCB().get(i).getUuid() == a1){
                                PCB remove = processScheduling.getBlockPCB().remove(i);
                                processScheduling.awake(remove);
                                break;
                            }
                        }
                    }
                    main.lockTime.unlock();
                    break;
                }
            }
           /**
            * 确认是否为闲置进程
            */
           if(processScheduling.getRunPCB() != processScheduling.getIdlePCB()){
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
                    preservation(processScheduling.getRunPCB());
                    if(code/4 == 0){
                        processScheduling.getRunPCB().setReason("A");
                        processScheduling.block("A");
                    }
                    if(code/4 == 1){
                        processScheduling.getRunPCB().setReason("B");
                        processScheduling.block("B");
                    }
                    if(code/4 == 2){
                        processScheduling.getRunPCB().setReason("C");
                        processScheduling.block("C");
                    }
                    recovery(processScheduling.getRunPCB());
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
}
