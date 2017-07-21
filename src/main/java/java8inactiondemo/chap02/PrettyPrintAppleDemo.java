package java8inactiondemo.chap02;

import java8inactiondemo.Apple;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huishen on 17/7/10.
 *
 */
public class PrettyPrintAppleDemo {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
            new Apple(80,"green"),
            new Apple(155, "green"),
            new Apple(120, "red"));

        prettyPrintApple(inventory, new OnlyHeavyPrint());
    }

    public static void prettyPrintApple(List<Apple> inventory, PrettyPrint<Apple> prettyPrint) {
        for (Apple apple : inventory) {
            prettyPrint.print(apple);
        }
    }

    public interface PrettyPrint<T> {
        void print(T t);
    }

    public static class OnlyHeavyPrint implements PrettyPrint<Apple> {
        @Override
        public void print(Apple apple) {
            System.out.println("apple.weight: " + apple.getWeight());
        }
    }

    // 还可以写其它PrettyPrint的子类

}
