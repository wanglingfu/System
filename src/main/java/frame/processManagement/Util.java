package frame.processManagement;

/**
 * @description: 编译String类型
 * @author: whj
 * @create: 2020-11-06 15:53
 **/
public class Util {

    /**
     * String文件转为byte文件
     * @param file
     * @return
     */
    public static byte[] getByteFile(String file){
        String[] split = file.split("d");
        split[0] = split[0] + 'd';
        String[] split1 = split[0].split("\n");
        byte[] bytes = new byte[split1.length];
        for (int i = 0; i < split1.length; i++) {
            split1[i].replaceAll("\r","");
            bytes[i] = compile(split1[i]);
        }
        return bytes;
    }

    /**
     * byte文件转为String文件
     * @param file
     * @return
     */
    public static String getStringFile(byte[] file){
        StringBuilder stringBuilder = new StringBuilder();
        for (Byte aByte : file) {
            String s = byteToString(aByte);
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * byte命令转为String命令
     * @param IR
     * @return
     */
    public static String byteToString(byte IR){
        if (IR == 0){
            return "x++";
        }
        else if (IR == 32){
            return "x--";
        }
        else if(IR == 96){
            return "end";
        }
        else if(IR < 0){
            int i =IR + 128;
            return "x="+i;
        }else{
            int i = (int)IR;
            int code = i/64;
            int[] device = {(i % 64) / 16, (i % 64) / 8};
            int[] time = {i%16,i%8};
            if(device[code] == 0){
                return "!A" + time[code];
            }
            else if(device[code] == 1){
                return "!B" + time[code];
            }
            else{
                return "!C" + time[code];
            }
        }
    }

    /**
     * byte命令转为String命令
     * @param IR
     * @return
     */
    public static byte compile(String IR){
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
        if (IR.equals("x++") || IR.equals("X++")) {
            return 0;
        }else if (IR.equals("x--")||IR.equals("X--")) {
            return 32;
        }else if (IR.equals("end")||IR.equals("END")) {
            return 96;
        }else if (chars[0] == '!') {
            if(chars[2] == '8' || chars[2] == '9'){
                int b = 0;
                if(chars[1] == 'A' || chars[1] == 'a') {
                    b = 0;
                } else if (chars[1] == 'B' || chars[1] == 'b') {
                    b = 16;
                } else if (chars[1] == 'C' || chars[1] == 'c') {
                    b = 32;
                }
                b = b + chars[2] - '0' ;
                return (byte) b;
            }else{
                int b = 64;
                if(chars[1] == 'A' || chars[1] == 'a')  {
                    b+=0;
                } else if (chars[1] == 'B' || chars[1] == 'b') {
                    b+=8;
                } else if (chars[1] == 'C' || chars[1] == 'c') {
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
