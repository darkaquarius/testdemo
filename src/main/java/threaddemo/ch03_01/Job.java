package threaddemo.ch03_01;

/**
 * Created by huishen on 17/6/28.
 *
 */
public class Job implements Runnable {

    private PrintQueue printQueue;

    public Job(PrintQueue printQueue){
        this.printQueue=printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Going to print a job");
        printQueue.printJob(new Object());
        System.out.println(Thread.currentThread().getName() + ": The document has been printed");
    }
}
