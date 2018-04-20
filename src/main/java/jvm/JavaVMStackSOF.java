package jvm;

/**
 * @author huishen
 * @date 18/3/20 下午3:01
 *
 * 线程请求的栈深度大于虚拟机所允许的最大深度,抛出 StackOverflowError
 *
 * VM options: -Xss256k
 *
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
