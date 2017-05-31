package demo;

import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by huishen on 16/10/24.
 */
public class LambdaTest {

    //pre java 8
    @Test
    public void test1() {
        //pre java 8
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");

        Collections.sort(list, (o1, o2) -> o1.compareTo(o2));
    }

    @Test
    public void test3() {
        //java 8
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        Collections.sort(list, String::compareTo);
        list.forEach(System.out::println);
    }

    @Test
    public void test4() {
        Man man1 = Man.builder().id(1).name("zhang").build();
        Man man2 = Man.builder().id(2).name("wang").build();
        Man man3 = Man.builder().id(3).name("zhao").build();
        Man man4 = Man.builder().id(4).name("li").build();

        List<Man> list1 = Arrays.asList(man1, man2, man3);
        List<Man> list2 = Arrays.asList(man1, man2, man4);

        List<Integer> ids1 = list1.stream().map(Man::getId).collect(Collectors.toList());
        List<Integer> ids2 = list1.stream().map(Man::getId).collect(Collectors.toList());

    }

}

@Data
@Builder
class Man {
    private int id;
    private String name;
}
