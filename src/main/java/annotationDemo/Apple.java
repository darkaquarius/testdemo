package annotationDemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huishen
 * @date 2018/10/19 下午2:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apple {

    @FruitName("Apple")
    private String appleName;

    @FruitColor(friutColor = FruitColor.Color.RED)
    private String appleColor;

    public static void main(String[] args) {
        Apple apple = new Apple();
        System.out.println(apple.getAppleName());
        System.out.println(apple.getAppleColor());
    }

}
