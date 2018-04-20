package jvm;

/**
 * @author huishen
 * @date 18/3/21 下午4:19
 *
 * 新生代 Minor GC
 *
 * -XX:+PrintGCDetails
 * -Xms20M -Xmx20M -Xmn10M
 * -XX:SurvivorRatio=8
 *
 */
public class MinorGC {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[2 * _1MB];    // 出现一次Minor GC
    }

}
