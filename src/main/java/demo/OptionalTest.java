package demo;

import org.junit.Test;

import java.util.Optional;

/**
 * @author huishen
 * @date 2019-05-17 15:48
 */
public class OptionalTest {

    /**
     *
     */
    @Test
    public void testOptional1() {
        String text = "Hello World";
        //         Pre-Java 8
        //        if (text != null) {
        //            System.out.println("Pre-java 8: text = " + text);
        //        }
        Optional<String> text1 = Optional.ofNullable(text);

        // ifPresent
        text1.ifPresent(t -> {
            System.out.println(t);
        });
    }

    @Test
    public void testOptional() {
        // String text = "Hello World";
        String text = null;
        byte[] bytes = Optional.ofNullable(text)
            .map(t -> {
                return t.getBytes();
            }).orElse(new byte[]{});
    }

    @Test
    public void testOptional2() {
        //        Java 8
        Integer integer = Optional.ofNullable("Hello World").map(String::length).orElse(-1);
        System.out.println(integer);
        //        Pre-Java 8
        //        if (text != null) ? text.length() : -1;
    }

    @Test
    public void testOptional3() {
        // String origin = "hello";
        String origin = null;
        String str = Optional.ofNullable(origin).orElse(null);
        System.out.println(str);
    }

}
