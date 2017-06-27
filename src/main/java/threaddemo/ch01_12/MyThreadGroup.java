package threaddemo.ch01_12;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class MyThreadGroup extends ThreadGroup {

    public MyThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("The thread %s has thrown an Exception\n",t.getId());
        e.printStackTrace(System.out);
        System.out.println("Terminating the rest of the Threads");
        interrupt();
    }
}
