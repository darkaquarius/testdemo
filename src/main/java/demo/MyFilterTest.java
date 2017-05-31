package demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by huishen on 16/12/27.
 */
public class MyFilterTest {

    public boolean compare(int i){
        if(i >= 10)
            return true;
        else
            return false;
    }

    public List<Integer> func(){
        List<Integer> list = Arrays.asList(1, 5, 10, 15);
        return list.stream().filter(l -> compare(l)).collect(Collectors.toList());
    }

    public static void main(String args[]){
        List<Integer> func = new MyFilterTest().func();
        System.out.println(func);
    }
}
