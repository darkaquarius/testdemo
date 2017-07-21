package java8inactiondemo.chap02;

import java8inactiondemo.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huishen on 17/7/10.
 *
 */
public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
            new Apple(80,"green"),
            new Apple(155, "green"),
            new Apple(120, "red"));
        // 绿苹果
        List<Apple> greenApples = filter(inventory, new AppleColorPredicate());
        // 大于150的绿苹果
        List<Apple> heavyApples = filter(inventory, new AppleColorPredicate());
        // 大于150的红苹果
        List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());

        // pre java8, 红苹果
        List<Apple> redApples = filter(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });

        // java8, 红苹果
        List<Apple> redApples2 = filter(inventory, apple -> "red".equals(apple.getColor()));
    }

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(predicate.test(apple))
                result.add(apple);
        }
        return result;
    }


    interface ApplePredicate {
        boolean test(Apple apple);
    }

    // 大于150的苹果
    public static class AppleWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    // 绿苹果
    public static class AppleColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    // 大于150的红苹果
    public static  class AppleRedAndHeavyPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) &&
                apple.getWeight() > 150;
        }
    }

}
