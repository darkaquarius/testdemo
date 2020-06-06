package demo;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CalendarDemo {

    private int execNums = 0;

    public static final int METRIC_TASK_INTERVAL = 1000 * 30;

    @Test
    public void test1() {
        Calendar calendar = Calendar.getInstance();

        int curentSeconds = calendar.get(Calendar.SECOND);
        if(execNums==0&&curentSeconds>=30){
            execNums = 1;
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);




        int seconds = execNums % 2;
        execNums++;
        if (execNums == 1000001) {
            execNums = 1;
        }

        long timestamp = calendar.getTimeInMillis() + seconds * METRIC_TASK_INTERVAL;
        Date now = new Date(timestamp);
    }

    @Test
    public void test2() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long timeInMillis = calendar.getTimeInMillis();
            System.out.println("timeInMillis:" + new Date(timeInMillis) + ", timeInMillis:" + timeInMillis);

            Thread.sleep(3000);
        }
    }

    @Test
    public void test3() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            getTime(System.currentTimeMillis());
            Thread.sleep(10_000);
        }
    }

    private Date getTime(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) <= 29 ? 0 : 30);
        System.out.println("timestamp: " + new Date(timestamp));
        System.out.println("date: " + calendar.getTime());
        System.out.println();
        return calendar.getTime();
    }

}
