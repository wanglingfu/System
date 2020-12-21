package frame.processManagement.Runnable;

import frame.processManagement.PCB;
import frame.processManagement.ProcessScheduling;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 执行进程
 * @author: whj
 * @create: 2020-10-19 22:30
 **/
public class CPU{
    /**
     * 数据寄存器
     */
    private Integer AX;
    /**
     * 中断标志寄存器
     */
    private int[] PSW = {0,0,0};
    /**
     * 指令寄存器
     */
    private String IR;
    /**
     * 程序计数器
     */
    private Integer PC = 0;
    /**
     * 运行中的进程文件
     */
    private byte[] file;
    /**
     * 是否运行完
     */
    private int flag ;
    /**
     * 运行结束之后显示进程的AX
     */
    private int finalAX;
    /**
     * 进程调度
     */
    private ProcessScheduling processScheduling;
    /**
     * 运行中进程uuid
     */
    private String uuid;
    /**
     * 系统时间
     */
    public int SystemTime=0;
    /**
     * 时间片
     */
    public int TimeSlice=6;
    /**
     * 设备时间
     */
    public int[] DeviceTime = {-1,-1,-1,-1,-1,-1,-1,-1};

    /**
     *进程锁
     */
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition1 = lock.newCondition();
    public static Condition condition2 = lock.newCondition();
    public CPU(int flag, ProcessScheduling processScheduling, String uuid) {
        this.flag = flag;
        this.processScheduling = processScheduling;
        this.uuid = uuid;
    }

    public Integer getAX() {
        return AX;
    }

    public void setAX(Integer AX) {
        this.AX = AX;
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFinalAX() {
        return finalAX;
    }

    public void setFinalAX(int finalAX) {
        this.finalAX = finalAX;
    }

    public int getSystemTime() {
        return SystemTime;
    }

    public void setSystemTime(int systemTime) {
        SystemTime = systemTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTimeSlice() {
        return TimeSlice;
    }

    public void setTimeSlice(int timeSlice) {
        TimeSlice = timeSlice;
    }

    public int[] getDeviceTime() {
        return DeviceTime;
    }

    public void setDeviceTime(int[] deviceTime) {
        DeviceTime = deviceTime;
    }

    /**
     * 恢复主要寄存器
     */
    public void recovery(PCB pcb){
        AX = pcb.getAX();
        PC = pcb.getPC();
        file = pcb.getFile();
        uuid = pcb.getUuid();
        TimeSlice = 6;
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
    public void cpu() throws InterruptedException {
       while(flag>0){
           lock.lock();
           condition1.await();
           /**
            * 一个系统时间执行一条指令
            */
           if (processScheduling.getRunPCB().getUuid() != uuid) {
               recovery(processScheduling.getRunPCB());
           }

           /**
            * 判断中断
            */
           if(PSW[0] == 1){
               psw1();
           }
           if(PSW[1] == 1){
               psw2();
           }
           if(PSW[2] == 1){
               psw3();
           }
           /**
            * 确认是否为闲置进程
            */
           if (processScheduling.getRunPCB().getUuid() != processScheduling.getIdlePCB().getUuid()) {
               int i = file[PC];
               PC++;
               //x++指令
               if (i == 0) {
                   AX++;
                   IR = "x++";
               }
               //x--指令
               else if (i == 32) {
                   AX--;
                   IR = "x--";
               }
               //end指令
               else if (i == 96) {
                   PSW[0] = 1;
                   finalAX = AX;
                   IR = "end";
               }

               //x=?指令
               else if (i < 0) {
                   AX = i + 128;
                   IR = "x=" + AX;
               }
               //！？？指令
               else {
                   /**
                    * code：是否为特殊
                    */
                   int code = i / 64;
                   int[] device = {(i % 64) / 16, (i % 64) / 8};
                   int[] time = {i % 16, i % 8};
                   preservation(processScheduling.getRunPCB());
                   processScheduling.getRunPCB().setTime(time[code]);
                   if (device[code] == 0) {
                       processScheduling.getRunPCB().setReason("A");
                       int a = processScheduling.block("A");
                       if (a < 9) {
                           DeviceTime[a-1] = time[code];
                       }
                       IR = "!A" + time[code];
                   }
                   if (device[code] == 1) {
                       processScheduling.getRunPCB().setReason("B");
                       int b = processScheduling.block("B");
                       if (b < 9) {
                           DeviceTime[b-1] = time[code];
                       }
                       IR = "!B" + time[code];
                   }
                   if (device[code] == 2) {
                       processScheduling.getRunPCB().setReason("C");
                       int c = processScheduling.block("C");
                       if (c < 9) {
                           DeviceTime[c-1] = time[code];
                       }
                       IR = "!C" + time[code];
                   }
                   recovery(processScheduling.getRunPCB());
               }
           }
           condition2.signal();
           lock.unlock();
       }
    }

    /**
     * 进程终止
     */
    private void psw1(){
        PSW[0] = 0;
        finalAX = processScheduling.getRunPCB().getAX();
        processScheduling.destroy();
        recovery(processScheduling.getRunPCB());
        flag--;
    }

    /**
     * 进程时间片结束
     */
    private void psw2(){
        PSW[1] = 0;
        processScheduling.setRunPCB(preservation(processScheduling.getRunPCB()));
        processScheduling.util(2);
        recovery(processScheduling.getRunPCB());
    }
    /**
     * 进程中断
     */
    private void psw3(){
        PSW[2] = 0;
        /**
         * 判断哪个设备已使用完
         */
        if (DeviceTime[0] == 0) {
            String a1 = processScheduling.getDevice().getDeviceTable().getA1();
            psw3Util(a1);
        }
        if (DeviceTime[1] == 0) {
            String a2 = processScheduling.getDevice().getDeviceTable().getA2();
            psw3Util(a2);
        }
        if (DeviceTime[2] == 0) {
            String b1 = processScheduling.getDevice().getDeviceTable().getB1();
            psw3Util(b1);
        }
        if (DeviceTime[3] == 0) {
            String b2 = processScheduling.getDevice().getDeviceTable().getB2();
            psw3Util(b2);
        }
        if (DeviceTime[4] == 0) {
            String b3 = processScheduling.getDevice().getDeviceTable().getB3();
            psw3Util(b3);
        }
        if (DeviceTime[5] == 0) {
            String c1 = processScheduling.getDevice().getDeviceTable().getC1();
            psw3Util(c1);
        }
        if (DeviceTime[6] == 0) {
            String c2 = processScheduling.getDevice().getDeviceTable().getC2();
            psw3Util(c2);
        }
        if (DeviceTime[7] == 0) {
            String c3 = processScheduling.getDevice().getDeviceTable().getC3();
            psw3Util(c3);
        }
    }
    private void psw3Util(String uuid){
        for (int i = 0; i < processScheduling.getBlockPCB().size(); i++) {
            if(processScheduling.getBlockPCB().get(i).getUuid() == uuid){
                PCB remove = processScheduling.getBlockPCB().remove(i);
                int[] awake = processScheduling.awake(remove);
                if(awake != null) {
                    DeviceTime[awake[0] - 1] = awake[1];
                }
                /**
                 * 是否为闲置进程
                 */
                if(processScheduling.getRunPCB().getUuid() != processScheduling.getIdlePCB().getUuid()){
                    processScheduling.getReadyPCB().add(remove);
                }else{
                    processScheduling.setRunPCB(remove);
                    recovery(remove);
                }
                break;
            }
        }
    }
    /**
     * 时间运行函数
     */
    public void time(){
        lock.lock();
        SystemTime++;
        if (processScheduling.getRunPCB().getUuid() != processScheduling.getIdlePCB().getUuid()) {
            TimeSlice--;
        }
        if (TimeSlice == 0) {
            TimeSlice = 6;
            PSW[1] = 1;
        }
        if (DeviceTime != null) {
            for (int i = 0; i < DeviceTime.length; i++) {
                DeviceTime[i]--;
                if (DeviceTime[i] == 0) {
                    PSW[2] = 1;
                }
            }
        }
        condition1.signal();
        lock.unlock();
    }

}
