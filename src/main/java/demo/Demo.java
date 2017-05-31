package demo;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huishen on 16/11/25.
 */

@Data
public class Demo {
    private String name;
    private Integer age;

    public static void main(String[] args){
        Demo demo=new Demo();
        demo.setAge(123);
        System.out.println(demo);
    }

    @Test
    public void test(){
        Integer integer = 10;
        String str = String.valueOf(integer);
        double aDouble = Double.parseDouble(str);
        System.out.println(aDouble);

        List<String> list = new ArrayList<>();
        list.add("10");
        double v = Double.parseDouble(list.get(0));

        if(aDouble == v){
            System.out.println("true");
        }
    }

}
