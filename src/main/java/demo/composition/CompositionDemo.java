package demo.composition;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by huishen on 17/6/6.
 *
 */
public class CompositionDemo {

    public static void main(String[] args) {
        InstrumentedSet<String> s =
            new InstrumentedSet<>(new HashSet<>());

        s.addAll(Arrays.asList("zhangsan", "lisi", "wangwu"));

        int addCount = s.getAddCount();
        System.out.println("aaddCount: " + addCount);
    }


}
