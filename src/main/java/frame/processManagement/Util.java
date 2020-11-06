package frame.processManagement;

/**
 * @description: 编译String类型
 * @author: whj
 * @create: 2020-11-06 15:53
 **/
public class Util {
    public static Byte compile(String IR){
        char[] chars = IR.toCharArray();
        /**
         * 编码规则：
         * x++:00000000
         * x—:00100000
         * !A?:01000xxx
         * !B?:01001xxx
         * !C?:01010xxx
         * end:01100000
         * x=?:1xxxxxxx
         * 特殊：
         * !A8:00001000
         * !A9:00001001
         * !B8:00011000
         * !B9:00011001
         * !C8:00101000
         * !C9:00101001
         */
        if (IR == "x++") {
            return 0;
        }else if (IR == "x--") {
            return 32;
        }else if (IR == "end") {
            return 96;
        }else if (chars[0] == '!') {
            if(chars[2] == '8' || chars[2] == '9'){
                int b = 0;
                if(chars[1] == 'A') {
                    b = 0;
                } else if (chars[1] == 'B') {
                    b = 16;
                } else if (chars[1] == 'C') {
                    b = 32;
                }
                b = b + chars[2] - '0' ;
                return (byte) b;
            }else{
                int b = 64;
                if(chars[1] == 'A') {
                    b+=0;
                } else if (chars[1] == 'B') {
                    b+=8;
                } else if (chars[1] == 'C') {
                    b+=16;
                }
                b = b + chars[2] - '0';
                return (byte) b;
            }

        }else {
            int i = 0;
            for (int j = 2; j < chars.length; j++) {
                i = i * 10 + chars[j] - '0';
            }
            i = i -128;
            return (byte)i;
        }
    }
}
