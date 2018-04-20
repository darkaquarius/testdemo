package jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author huishen
 * @date 18/3/20 下午4:04
 *
 * 使用unSafe分配本机内存
 *
 * VM options: -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }

}
