package frame.DeviceManagement;

/**
 * @ClassName Device
 * @Description TODO
 * @Author wlf
 * @Date 2020/10/19 0:05
 * @Version 1.0
 **/
public class Device {
    private  int A ;
    private  int B ;
    private  int C ;

    public Device(int A, int B, int C){
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }
}
