package direct;

import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

public class DirectMemory {

    @Test
    public void test1() throws InterruptedException {
        DirectBuffer bb = (DirectBuffer) ByteBuffer.allocateDirect(1024);
        bb.cleaner().clean();
    }

}
