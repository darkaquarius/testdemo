package demo;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Created by huishen on 16/10/18.
 * 文件io
 */

public class FileTest {

    /**
     * 打印某个目录下的所有文件
     */
    @Test
    public void test1() {
        File dest = new File("/Users/huishen");
        // 判断该路径是否是目录
        if (dest.exists() && dest.isDirectory()) {
            String[] fileList = dest.list();
            if (fileList != null) {
                Arrays.stream(fileList).forEach(System.out::println);
            }
        }
    }

    /**
     * 获取文件的大小
     * File的length()方法返回的类型为long，最大8388608T
     * FileInputStream的avaliable()方法返回值是int, 最大2G
     * channel.size()方法返回的是long,最大8388608T
     */
    @Test
    public void test2() {
        // 1.file.length
        File file = new File("/Users/huishen/ttt");
        if (file.exists() && file.isFile()) {
            long length = file.length();
            System.out.println("文件大小，file.length: " + length);
        }

        // 2.inputStream.available()
        try (FileInputStream inputStream = new FileInputStream("/Users/huishen/ttt")) {
            int available = inputStream.available();
            System.out.println("文件大小，inputStream.available(): " + available);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3.channel.size()
        if (file.exists() && file.isFile()) {
            try (FileChannel channel = new FileInputStream(file).getChannel()) {
                long size = channel.size();
                System.out.println("文件大小，channel.size(): " + size);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
