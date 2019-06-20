package thread.interrupt;

/**
 * @author huishen
 * @date 2019-06-20 11:07
 *
 * 优雅地终止线程
 */
public class StopDemo {

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.start();
        Thread.sleep(10);
        task.stop = true;
        Thread.sleep(1000);
    }

    public static class Task implements Runnable  {

        public volatile boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                System.out.println("subThread run......");
            }
            System.out.println("subThread ended.");
        }
    }

}
