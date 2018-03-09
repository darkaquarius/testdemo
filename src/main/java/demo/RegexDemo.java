package demo;

import org.junit.Test;

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

    @Test
    public void test2() {
        String text = "John writes about this, and John Doe writes about that,"
            + " and John Wayne writes about everything.";
        String regex = "(John) (.+?) ";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        boolean isFind = matcher.find();
        int start = matcher.start();
        int end = matcher.end();
        System.out.println("start = " + start);
        System.out.println("end = " + end);
        // 0代表全部匹配
        System.out.println("found group: group(0) is " + matcher.group(0));
        // 1代表匹配第一个括号内的内容，2代表匹配第2个括号内的内容
        System.out.println("found group: group(1) is " + matcher.group(1) + ", group(2) is " + matcher.group(2));
    }

}
