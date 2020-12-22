import org.junit.Test;

import java.util.Random;

/**
 * @description:
 * @author: whj
 * @create: 2020-12-22 21:44
 **/
public class dasda {
    @Test
    public void test(){
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int i1 = random.nextInt(20);
            if(i1 >= 0 && i1 <= 5){
                System.out.println("x++");
            }else if(i1 > 5 && i1 <= 10){
                System.out.println("x--");
            } else if(i1 == 11){
                int i2 = random.nextInt(10) + 1;
                System.out.println("!A" + i2);
            }else if(i1 == 12){
                int i2 = random.nextInt(10) + 1;
                System.out.println("!B" + i2);
            }else if(i1 == 13){
                int i2 = random.nextInt(10) + 1;
                System.out.println("!C" + i2);
            }else if(i1 > 13){
                int i2 = random.nextInt(100) + 1;
                System.out.println("x=" + i2);
            }
        }
    }
}
