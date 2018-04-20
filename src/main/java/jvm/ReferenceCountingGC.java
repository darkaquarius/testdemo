package jvm;

/**
 * @author huishen
 * @date 18/3/20 下午4:25
 *
 * 引用计数算法的缺陷：很难解决对象之间相互循环引用的问题
 *
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    /**
     * 唯一意义：占点内存
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // objA和objB是否能被收回？
        System.gc();
    }

}
