package demo;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by huishen on 17/2/17.
 */
public class OutputStreamTest {

    @Test
    public void test1() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("/data/hui.txt"));
            fos.write("hello world".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
