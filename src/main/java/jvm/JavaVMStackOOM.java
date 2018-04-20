package jvm;

/**
 * @author huishen
 * @date 18/3/20 下午3:23
 *
 * 创建线程导致内存溢出
 *
 * VM options: -Xss2M
 *
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() -> {dontStop();});

            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }

}
