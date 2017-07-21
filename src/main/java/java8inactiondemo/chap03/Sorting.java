package java8inactiondemo.chap03;

import java8inactiondemo.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huishen on 17/7/10.
 *
 */
public class Sorting {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
            new Apple(80,"green"),
            new Apple(155, "green"),
            new Apple(120, "red"));

        // inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        inventory.sort(Comparator.comparing(Apple::getWeight));
    }

}
