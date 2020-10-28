package frame.processManagement;

import frame.processManagement.Runnable.CPU;

/**
 * @description: 中断
 * @author: whj
 * @create: 2020-10-16 22:48
 **/
public class Interrupt{
    private int PSW;

    public Interrupt(int PSW) {
        this.PSW = PSW;
    }

    public void interrupt() {
        ProcessScheduling processScheduling = new ProcessScheduling();
        switch (PSW){
            case 1:{
                processScheduling.destroy();
                if(CPU.readyPCB.size()!=0){
                    CPU.runPCB = CPU.readyPCB.remove();
                }
                break;
            }
            case 2:{
                CPU.runPCB.getRegister().setPSW(0);
                CPU.readyPCB.add(CPU.runPCB);
                CPU.runPCB = CPU.readyPCB.remove();
            }
            case 3:{
                CPU.runPCB.getRegister().setPSW(0);
                processScheduling.block();
            }
            default:break;
        }
    }
}
