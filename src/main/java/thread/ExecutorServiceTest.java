package thread;

/**
 * Created by huishen on 17/2/27.
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTest {
    public static void main(String[] args) {

        // ExecutorService还有一个子类，ThreadPoolExecutor
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建一个固定大小的线程执行器，有最大线程数，
        // 超过这个最大线程数，执行器不再创建额外的线程，剩下的任务将被阻塞直到执行器有空闲的线程
        Executors.newFixedThreadPool(10);

        List<Future<String>> resultList = new ArrayList<>();

        // 创建10个任务并执行
        for (int i = 0; i < 10; i++) {
            // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            // 将任务执行结果存储到List中
            resultList.add(future);
        }
        executorService.shutdown();

        // 遍历任务的结果
        for (Future<String> fs : resultList) {
            try {

                //如果Executor后台线程池还没有完成Callable的计算，这调用返回Future对象的get()方法，会阻塞直到计算完成!!!
                System.out.println(fs.get()); // 打印各个线程（任务）执行的结果
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                executorService.shutdownNow();
                e.printStackTrace();
                return;
            }
        }
    }
}

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    /**
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
     *
     * @return String
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        System.out.println("call()方法被自动调用,干活！！！" + Thread.currentThread().getName());
        if (new Random().nextBoolean())
            throw new TaskException("Meet error in task." + Thread.currentThread().getName());
        // 一个模拟耗时的操作
        for (int i = 999999999; i > 0; i--)
            ;
        return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
    }
}

class TaskException extends Exception {
    public TaskException(String message) {
        super(message);
    }
}
