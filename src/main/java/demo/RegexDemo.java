package demo;

import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huishen on 17/5/25.
 *
 */
public class RegexDemo {

    //判断手机号是否合法
    @Test
    public void test1() {
        // String regex = "^1\\d{10}$";
        String regex = "^1[34578]\\d{9}$";

        if (!"13888888888".matches(regex)) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }

    /**
     * 2个括号是分开的
     */
    @Test
    public void test2() {
        String text = "John writes about this, and John Doe writes about that,"
            + " and John Wayne writes about everything.";
        String regex = "(John) (.+?) ";
        matcher(text, regex);
    }

    /**
     * 2个括号有包含关系
     */
    @Test
    public void test3() {
        String text = "John writes about this, and John Doe writes about that,"
            + " and John Wayne writes about everything.";
        String regex = "(John (.+?))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        matcher(text, regex);
    }

    private void matcher(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            System.out.println("start = " + start);
            System.out.println("end = " + end);
            // 0代表全部匹配
            System.out.println("found group: group(0) is: " + matcher.group(0));
            // 1代表匹配第一个括号内的内容，2代表匹配第2个括号内的内容
            System.out.println("found group: group(1) is: " + matcher.group(1));
            System.out.println("found group: group(2) is: " + matcher.group(2));
        }
    }

    @Test
    public void test4() {
        String urlPath = "/noble-web/page/";
        Pattern compile = Pattern.compile(urlPath, Pattern.CASE_INSENSITIVE);
        boolean b1 = compile.matcher("/noble-web/pages").find();
        boolean b2 = compile.matcher("/noble-web/page/asd/asd").find();
    }

    @Test
    public void test5() {
        long l = getInfluxdbQueryPoint(1574920700000L) ;
        System.out.println(l);
    }

    public long getInfluxdbQueryPoint(long now) {
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
        return queryPoint * 1000 * 1000;
    }

    @Test
    public void test6() {
        HashSet<Long> records = new HashSet<Long>();
//        records.add(10L);
//        records.add(1L);
//        records.add(30L);
        long maxRecords = CollectionUtils.isEmpty(records) ? 0 : Collections.max(records);
    }

//    @Test
//    public void test7() {
//        String urlPath = "/starwar/task/listen/layout/center/home/ss";
//        Pattern compile = Pattern.compile(urlPath, Pattern.CASE_INSENSITIVE);
//        boolean b1 = compile.matcher("/home/*").find();
////        boolean b2 = compile.matcher("/noble-web/page/asd/asd").find();
//        System.out.println(b1);
//    }


    @Test
    public void test7() {
        String urlPath1 = "^/home/.*";
        Pattern compile1 = Pattern.compile(urlPath1, Pattern.CASE_INSENSITIVE);

        String urlPath2 = "/home/*";
        Pattern compile2 = Pattern.compile(urlPath2, Pattern.CASE_INSENSITIVE);

        String urlPath3 = "/home";
        Pattern compile3 = Pattern.compile(urlPath3, Pattern.CASE_INSENSITIVE);

        System.out.println("/home/test");
        System.out.println("compile1: " + (compile1.matcher("/home/test").find()));
        System.out.println("compile2: " + (compile2.matcher("/home/test").find()));
        System.out.println("compile3: " + (compile3.matcher("/home/test").find()));
        System.out.println("==============================\n");
        System.out.println("/in/home/test");
        System.out.println("compile1: " + (compile1.matcher("/in/home/test").find()));
        System.out.println("compile2: " + (compile2.matcher("/in/home/test").find()));
        System.out.println("compile3: " + (compile3.matcher("/in/home/test").find()));
        System.out.println("==============================\n");
        System.out.println("/in-home/test");
        System.out.println("compile1: " + (compile1.matcher("/in-home/test").find()));
        System.out.println("compile2: " + (compile2.matcher("/in-home/test").find()));
        System.out.println("compile3: " + (compile3.matcher("/in-home/test").find()));
        System.out.println("==============================\n");
        System.out.println("/in/homes/test");
        System.out.println("compile1: " + (compile1.matcher("/in/homes/test").find()));
        System.out.println("compile2: " + (compile2.matcher("/in/homes/test").find()));
        System.out.println("compile3: " + (compile3.matcher("/in/homes/test").find()));
        System.out.println("==============================\n");
        System.out.println("/home");
        System.out.println("compile1: " + (compile1.matcher("/home").find()));
        System.out.println("compile2: " + (compile2.matcher("/home").find()));
        System.out.println("compile3: " + (compile3.matcher("/home").find()));
        System.out.println("==============================\n");
        System.out.println("/home/");
        System.out.println("compile1: " + (compile1.matcher("/home/").find()));
        System.out.println("compile2: " + (compile2.matcher("/home/").find()));
        System.out.println("compile3: " + (compile3.matcher("/home/").find()));
        System.out.println("==============================\n");
    }

    @Test
    public void test8() {
        String urlPath1 = "^/home/.*";
        Pattern compile1 = Pattern.compile(urlPath1, Pattern.CASE_INSENSITIVE);
        System.out.println("compile1: " + (compile1.matcher("/home").find()));
    }

    @Test
    public void test9() {
        String urlPath1 = "/business-album-presale-web/v1/album/.+/detail/ts-.+";
        Pattern compile1 = Pattern.compile(urlPath1, Pattern.CASE_INSENSITIVE);
        System.out.println("compile1: " + (compile1.matcher("/business-open-web/recharge").find()));
    }

}
