package demo.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by huishen on 17/11/2.
 * 从Channel写到Buffer,调用通道的read(Buffer buffer)方法
 * put()方法写到Buffer里，调用Buffer的put方法
 *
 * 从Buffer读取数据到Channel，调用通道的write(Buffer buffer)方法
 * 使用get()方法从Buffer中读取数据,调用Buffer的get方法
 *
 *
 */

public class ChannelsDemo {

    /**
     * 从FileOutputStream中获取FileChannel
     */
    @Test
    public void test1() throws IOException {
        FileOutputStream fos = new FileOutputStream("/Users/huishen/test4.txt");
        FileChannel channel = fos.getChannel();
        channel.write(ByteBuffer.wrap("Some text1\nSome text2 ".getBytes()));
        fos.close();
    }

    /**
     * 从RandomAccessFile中获取FileChannel
     */
    @Test
    public void test2() throws Exception {
        RandomAccessFile raf = new RandomAccessFile("/Users/huishen/test4.txt", "rw");
        FileChannel channel = raf.getChannel();
        //设置通channel的文件位置 为末尾
        channel.position(channel.size());
        channel.write(ByteBuffer.wrap("Some more\nSome".getBytes()));
        raf.close();
    }

    /**
     * 从FileInputStream中获取FileChannel
     */
    @Test
    public void test3() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/huishen/test1.txt");
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        //调用此方法为一系列通道写入或相对获取操作做好准备
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println((char) buffer.get());
        }
        fis.close();
    }

    /**
     * 文件复制，方法一：
     * 打开两个FileChannel，一个用于读，另一个用于写
     */
    @Test
    public void copyFile1() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/huishen/test1.txt");
        FileOutputStream fos = new FileOutputStream("/Users/huishen/test1_copy.txt");

        FileChannel channel1 = fis.getChannel();
        FileChannel channel2 = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (channel1.read(buffer) != -1) {
            // 为写入操作做准备
            buffer.flip();
            channel2.write(buffer);
            buffer.clear();
        }

        fis.close();
        fos.close();
    }

    /**
     * 文件复制，方法二：
     * transferTo或者transferFrom方法传输通道之间的数据
     */
    @Test
    public void copyFile2() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/huishen/test1.txt");
        FileOutputStream fos = new FileOutputStream("/Users/huishen/test1_copy.txt");

        FileChannel channel1 = fis.getChannel();
        FileChannel channel2 = fos.getChannel();
        channel1.transferTo(0, channel1.size(), channel2);

        fis.close();
        fos.close();
    }




}
