package demo;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.SequenceInputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by huishen on 16/10/18.
 * 文件io
 */

public class IOTest {

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

    /**
     * inputStream.read(byte[] b)
     */
    @Test
    public void test3() {
        try (FileInputStream inputStream = new FileInputStream("/Users/huishen/nginx.conf")) {
            byte[] bytes = new byte[512];
            int i = 0;
            while ((i = inputStream.read(bytes)) != -1) {
                System.out.println(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Test
    public void test4() {
        // TODO自动生成的方法存根
        // 一次取出的字节数大小,缓冲区大小
        byte[] buffer = new byte[512];
        int numberRead = 0;
        try (FileInputStream input = new FileInputStream("/Users/huishen/Downloads/WechatIMG162.png");
            FileOutputStream out = new FileOutputStream("/Users/huishen/Downloads/WechatIMG162_copy.png")) {

            while ((numberRead = input.read(buffer)) != -1) {
                // 否则会自动被填充0
                out.write(buffer, 0, numberRead);
            }
        } catch (IOException e) {
            // TODO自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /**
     * ObjectInputStream and ObjectOutputStream
     */
    public void test5() {
        // 参考TransientDemo的test()方法
    }

    /**
     * PushbackInputStream类继承了FilterInputStream类
     * PushbackInputStream类的作用就是能够在读取缓冲区的时候提前知道下一个字节是什么
     */
    @Test
    public void test6() throws IOException {
        String str = "hi,java";

        int temp = 0;

        try (ByteArrayInputStream bat = new ByteArrayInputStream(str.getBytes());
             PushbackInputStream push = new PushbackInputStream(bat)) {

            while ((temp = push.read()) != -1) {
                if (temp == ',') {
                    push.unread(temp);
                    temp = push.read();
                    // 输出回退的字符
                    System.out.print("(回退" + (char) temp + ") ");
                } else {
                    // 否则输出字符
                    System.out.print((char) temp);
                }
            }
        }

    }

    /**
     * SequenceInputStream
     */
    @Test
    public void test7() {
        // 创建一个合并流的对象
        SequenceInputStream sis = null;
        // 创建输出流。
        BufferedOutputStream bos = null;
        try {
            // 构建流集合。
            Vector<InputStream> vector = new Vector<>();
            vector.addElement(new FileInputStream("/Users/huishen/test1.txt"));
            vector.addElement(new FileInputStream("/Users/huishen/test2.txt"));
            vector.addElement(new FileInputStream("/Users/huishen/test3.txt"));
            Enumeration<InputStream> e = vector.elements();

            sis = new SequenceInputStream(e);

            bos = new BufferedOutputStream(new FileOutputStream("/Users/huishen/test4.txt"));
            // 读写数据
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = sis.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (sis != null)
                    sis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FileReader
     */
    @Test
    public void test8() {
        char[] buffer=new char[512];
        int numberRead=0;

        try (FileReader reader = new FileReader("/Users/huishen/test1.txt");
             PrintWriter writer = new PrintWriter(System.out)) {
            while ((numberRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, numberRead);
                writer.flush();
            }
        } catch (IOException e) {
            // TODO自动生成的 catch 块
            e.printStackTrace();
        }
    }

    // @Test
    // public void test9() {
    //
    // }


}
