package threaddemo.ch01_09;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class Main {

    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread has finished");
    }

}
