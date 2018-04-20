package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huishen
 * @date 18/4/1 下午3:06
 */
public class GenericType {

    // public static String method(List<String> list) {
    //     System.out.println("invoke method<String> list");
    //     return "";
    // }

    public static int method(List<Integer> list) {
        System.out.println("invoke method<Integer> list");
        return 1;
    }

    public static void main(String[] args) {
        // method(new ArrayList<String>());
        method(new ArrayList<Integer>());
    }

}
