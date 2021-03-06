package annotationDemo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huishen
 * @date 2018/10/19 下午2:52
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FruitColor {

    public enum Color {
        BLUE,
        RED,
        GREEN
    }

    Color friutColor() default Color.GREEN;

}
