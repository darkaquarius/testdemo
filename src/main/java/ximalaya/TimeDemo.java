package ximalaya;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TimeDemo {

    /**
     * 把开始结束时间(时间戳)换算成influxdb中的时间
     */
    @Test
    public void test01() {
        long startTime = 1577690866202L;
        long endTime = 1577680556346l;
        long startTimeNanoSec = getInfluxdbQueryPoint(startTime) * 1000 * 1000;
        long endTimeNanoSec = getInfluxdbQueryPoint(endTime) * 1000 * 1000;
        System.out.println("startTime: " + startTimeNanoSec);
        System.out.println("endTime: " + endTimeNanoSec);
    }

    public static long getInfluxdbQueryPoint(long now) {
        //时间由ms转换成s从而去掉后面多余的尾数
        long queryPoint = now / 1000;

        int seconds = (int) queryPoint % 60;

        if (seconds == 0 || seconds == 30) {
        } else if (seconds > 30) {
            queryPoint = queryPoint - (seconds - 30);
        } else {
            queryPoint = queryPoint - seconds;
        }

        //时间由s转换成ms
        queryPoint *= 1000;
        return queryPoint;
    }




}
