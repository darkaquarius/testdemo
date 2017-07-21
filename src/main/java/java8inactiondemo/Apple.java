package java8inactiondemo;

import lombok.Data;

/**
 * Created by huishen on 17/7/10.
 *
 */

@Data
public class Apple {
    private Integer weight = 0;
    private String color = "";

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public String toString() {
        return "Apple{" +
            "color='" + color + '\'' +
            ", weight=" + weight +
            '}';
    }

}