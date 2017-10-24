package demo;

import lombok.Data;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by huishen on 16/11/25.
 *
 */

@Data
public class Demo {
    private String name;
    private Integer age;

    public static void main(String[] args){
        Demo demo=new Demo();
        demo.setAge(123);
        System.out.println(demo);
    }

    @Test
    public void test(){
        Integer integer = 10;
        String str = String.valueOf(integer);
        double aDouble = Double.parseDouble(str);
        System.out.println(aDouble);

        List<String> list = new ArrayList<>();
        list.add("10");
        double v = Double.parseDouble(list.get(0));

        if(aDouble == v){
            System.out.println("true");
        }
    }

    @Test
    public void test01() {
        String s = LocalDate.now().toString();
        System.out.println(s);
        // new StringBuffer().append("key").append("com.loan.test").append(LocalDate.now()).toString();

    }

    @Test
    public void test02() {
        System.out.println(Objects.equals("a", "a"));
    }

    @Test
    public void test03() {
        String str = "a, b, c,,";
        String[] split = str.split(",");
        // 预期大于 3,结果是 3
        System.out.println(split.length);
        Stream.of(split).forEach(System.out::println);
    }

    @Test
    public void test04() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        // ArrayList#subList()返回的是ArrayList的内部类SubList
        List<String> subList = list.subList(0, 1);
        subList.forEach(System.out::println);
        list.add("c");    // error! ConcurrentModificationException
        subList.forEach(System.out::println);
    }

    /**
     * 集合转数组，必须使用集合的toArray(T[] array)方法
     */
    @Test
    public void test05() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String[] strings = list.toArray(new String[]{});
        Stream.of(strings).forEach(System.out::println);
    }

    // 不要在foreach里进行元素的add/remove操作
    // 请使用Iterator方式
    @Test
    public void test06() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        for (String item : list) {
            if ("1".equals(item)) {
                list.remove(item);
            }
        }
        list.forEach(System.out::println);
    }

    @Test
    public void test07() {
        String str = "asd";
        String[] split = str.split("::");
    }


}
