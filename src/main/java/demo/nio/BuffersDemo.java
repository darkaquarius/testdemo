package demo.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by huishen on 17/11/2.
 *
 */

public class BuffersDemo {

    @Test
    public void test() throws Exception {
        RandomAccessFile file = new RandomAccessFile("/Users/huishen/test1.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (channel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
        }

        buffer.clear();
        file.close();
        channel.close();
    }


}
