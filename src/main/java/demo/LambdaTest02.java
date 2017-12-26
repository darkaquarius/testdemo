package demo;


import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by huishen on 17/11/26.
 * flatMap
 *
 */

public class LambdaTest02 {

    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 5, 10);
        // List<Apple> map = map(list, (Integer i) -> new Apple(i));
        List<Apple> map = map(list, Apple::new);
    }

    public List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (Integer i : list) {
            result.add(f.apply(i));
        }

        return result;
    }

    // [1, 3], [1, 4], [2, 3], [2, 4], [3, 3], [3, 4]
    @Test
    public void test2() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        Stream<Stream<int[]>> streamStream = numbers1.stream()
            .map(i -> numbers2.stream()
                    .map(j -> new int[]{i, j}));

        Stream<int[]> stream = numbers1.stream()
            .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}));

        Stream<int[]> stream1 = numbers2.stream().map(j -> new int[]{j});
    }

    @Test
    public void test3() {
        String[] words = {"hello", "world"};
        Stream<String[]> stream = Arrays.stream(words)
            .map(w -> w.split(""));
        Stream<String> stringStream =
            stream.flatMap(Arrays::stream);

        List<String> collect = stringStream.collect(Collectors.toList());
    }

}

@Data
class Apple {
    private int weight;

    public Apple(int weight) {
        this.weight = weight;
    }
}
