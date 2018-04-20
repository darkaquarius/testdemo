package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huishen
 * @date 18/3/20 下午2:14
 *
 * java堆内存溢出异常测试
 *
 * VM options: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/Users/huishen/git/testdemo/java_pid.hprof
 *
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }

}
