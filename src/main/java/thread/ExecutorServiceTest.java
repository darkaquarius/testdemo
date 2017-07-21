package thread;

/**
 * Created by huishen on 17/2/27.
 * ExecutorService and Future
 */
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ExecutorServiceTest {

    // Future
    @Test
    public void test() {
        // ExecutorService还有一个子类，ThreadPoolExecutor
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建一个固定大小的线程执行器，有最大线程数，
        // 超过这个最大线程数，执行器不再创建额外的线程，剩下的任务将被阻塞直到执行器有空闲的线程
        // Executors.newFixedThreadPool(10);

        // 创建单个线程
        // Executors.newSingleThreadExecutor();

        List<Future<String>> resultList = new ArrayList<>();

        // 创建10个任务并执行
        for (int i = 0; i < 10; i++) {
            Future<String> future = executorService.submit(new TaskWithResult(i));
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

    // FutureTask
    @Test
    public void test2() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<FutureTask<String>> taskList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            FutureTask<String> task = new FutureTask<>(new TaskWithResult(i));
            taskList.add(task);
            executorService.submit(task);
        }

        for (FutureTask<String> task : taskList) {
            try {
                System.out.println(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}

