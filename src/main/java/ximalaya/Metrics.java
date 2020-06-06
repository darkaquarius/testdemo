package ximalaya;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;


public class Metrics {

  /**
   * Metrics数据时间间隔
   */
  public static final int METRIC_TASK_INTERVAL = 1000 * 30;

  private int count;

  /**
   * 获取监控数据类型，默认使用Metrics具体实现类名称作为类型
   *
   * @return 监控数据类型
   */
  public String getType() {
    return this.getClass().getSimpleName();
  }

  /**
   * 获取监控数据时间戳，现在为30s时间间隔上传一次数据
   *
   * @return 时间戳
   */
  public long getTimestamp() {

    int factor = count % 2;
    count++;

    if (count == 1000001) {
      count = 0;
    }

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    long timeInMillis = calendar.getTimeInMillis();
    long timestamp = timeInMillis + factor * METRIC_TASK_INTERVAL;

    System.out.println(String.format("timeInMillis:%s, timestamp:%s", new Date(timeInMillis), new Date(timestamp)));
    return timestamp;
  }

  public static void main(String[] args) throws InterruptedException {
    Metrics metrics = new Metrics();
    for (int i = 0; i < 100; i++) {
      long timestamp = metrics.getTimestamp();
      Thread.sleep(1000);
    }
  }

}
