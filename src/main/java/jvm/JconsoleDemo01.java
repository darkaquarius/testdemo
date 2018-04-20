package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huishen
 * @date 18/3/22 上午11:28
 *
 * Jconsole监控代码：内存监控
 *
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 *
 */
public class JconsoleDemo01 {

    static class OOMObject {
        public byte[] placeHolder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws Exception {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000);
        Thread.sleep(10 * 60 * 1000);
    }

}
