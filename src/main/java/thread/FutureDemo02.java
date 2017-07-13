package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by huishen on 17/7/12.
 * 由于future.get()方法会阻塞，实际上变成了同步执行
 */
public class FutureDemo02 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // List<Future<String>> resultList = new ArrayList<>();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // final int f = i;
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(() -> {
                System.out.println("call()方法被自动调用,干活！！！ " + Thread.currentThread().getName());
                //一个模拟耗时的操作
                for (int j = 999999999; j > 0; j--);
                return "call()方法被自动调用，任务的结果是：" + " " + Thread.currentThread().getName();
            });
            //将任务执行结果存储到List中
            try {
                // results.add(future.get());
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }


    }

}
