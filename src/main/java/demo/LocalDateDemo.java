package demo;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by huishen on 16/11/26.
 */
public class LocalDateDemo {
    @Test
    public void localDateTest(){
        String startDate = "2016-11-21";
        String endDate = "2016-11-22";
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        LocalDate localStartDate1 = localStartDate.plusDays(1);

        System.out.println(localStartDate1.equals(localEndDate));

    }

    @Test
    public void test(){
        LocalDate now = LocalDate.now();
        LocalDate past = LocalDate.now().minusDays(1000);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = now.format(dateFormatter);
        String pastDay = past.format(dateFormatter);
        System.out.println(today);
        System.out.println(pastDay);
    }

    @Test
    public void test2() {
        LocalDate date = LocalDate.now();
        System.out.println(date.toString());
    }

    @Test
    public void test3() {
        LocalDate min = LocalDate.MIN;
        System.out.println(min);
    }

}
