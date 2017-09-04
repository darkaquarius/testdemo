package demo;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by huishen on 17/8/30.
 *
 */

public class Timestampdemo {

    @Test
    public void test01() {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        System.out.println(d);
    }

    @Test
    public void test02() {
        Date date=new Date();
        System.out.println(date.getTime());  //获取当前时间戳
    }

    @Test
    public void test03() {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timeStamp = Timestamp.valueOf(now);
        System.out.println(timeStamp.getTime());
    }

}
