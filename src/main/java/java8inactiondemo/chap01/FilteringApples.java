package java8inactiondemo.chap01;

import java8inactiondemo.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by huishen on 17/7/10.
 *
 */
public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
            new Apple(80, "green"),
            new Apple(155, "green"),
            new Apple(120, "red")
        );

        // pre java8
        List<Apple> greenApples1 = filterGreenApple(inventory);
        List<Apple> heavyApples1 = filterHeavyApple(inventory);

        // java8, lambda
        List<Apple> greenApples2 = filterApple(inventory, apple -> "green".equals(apple.getColor()));
        List<Apple> heavyApples2 = filterApple(inventory, apple -> apple.getWeight() > 150);

        // java8, method reference
        List<Apple> greenApples3 = filterApple(inventory, FilteringApples::isGreenApple);
        List<Apple> heavyApples3 = filterApple(inventory, FilteringApples::isHeavyApple);
    }

    // pre java8
    public static List<Apple> filterGreenApple(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor()))
                result.add(apple);
        }

        return result;
    }

    // pre java8
    public static List<Apple> filterHeavyApple(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150)
                result.add(apple);
        }

        return result;
    }

    // java8
    // filterHeavyApple和filterHeavyApple只有if语句不一样，可以"在调用方法的时候"把方法传递进来
    public static List<Apple> filterApple(List<Apple> inventory, Predicate<Apple> t) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (t.test(apple))
                result.add(apple);
        }

        return result;
    }

    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }



}