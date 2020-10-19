import frame.FileManagement.Disk;
import frame.FileManagement.FileUtil;
import org.junit.Test;

/**
 * @author ：Vizzk
 * @description：文件测试类
 * @date ：2020/10/18 10:18
 */
public class TestFile {
    @Test
    public void testDisk() throws Exception{
        Disk disk;
        disk = new Disk();
        FileUtil FILE_UTIL= new FileUtil(disk.getDisk());
        String S = "/ad/cc.e";
        FILE_UTIL.makeDirectory(S);
    }
}
