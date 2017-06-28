package demo;

import org.junit.Test;

/**
 * Created by huishen on 16/11/5.
 *
 */
public class java8Test {

    @Test
    public void testFunctionalInterface1(){
        //pre java 8
        Convert<String, Integer> c = new Convert<String, Integer>(){
            @Override
            public Integer convert(String str){
                return Integer.parseInt(str);
            }
        };
        System.out.println(c);
    }

    @Test
    public void testFunctionalInterface2(){
        //java 8
        //lambda expression
        // Convert<String, Integer> c = str -> Integer.parseInt(str);
        //method reference
        Convert<String, Integer> c = Integer::parseInt;
        System.out.println(c);
    }


}

@FunctionalInterface
interface Convert<F, T> {
    T convert(F from);
}

