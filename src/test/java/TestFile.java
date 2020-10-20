import frame.FileManagement.Disk;
import frame.FileManagement.FileUtil;
import org.junit.Test;

import java.lang.System;
import java.util.Arrays;

/**
 * @author ：Vizzk
 * @description：文件测试类
 * @date ：2020/10/18 10:18
 */
public class TestFile {
    @Test
    public void testDisk() throws Exception{

        FileUtil fileUtil= new FileUtil();
        String S = "/ad/sdf/cc.e";
        fileUtil.makeDirectory(S);
        /*byte[][] bytes = fileUtil.formatPath(S);
        for(byte[] b:bytes){
            System.out.println(Arrays.toString(b));
            System.out.println(fileUtil.bytesToString(b));
        }*/
    }
}
