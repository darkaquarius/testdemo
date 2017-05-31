package demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huishen on 16/10/20.
 */
public class CurrentTimeMillisTest {

    @Test
    public void testCurrentTimeMillis(){
        Map<String, String> map = new HashMap();
        map.put("1", System.currentTimeMillis() + "a");
        map.put("2", System.currentTimeMillis() + "a");
        map.put("3", System.currentTimeMillis() + "a");

        for(Map.Entry<String, String> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

    }

    //System.currentTimeMillis和System.nanoTime之间的区别
    @Test
    public void testNanoTime(){
        Map<String, String> map = new HashMap<>();
        map.put("1", System.nanoTime() + "a");
        map.put("2", System.nanoTime() + "a");
        map.put("3", System.nanoTime() + "a");

        for(Map.Entry<String, String> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

    }

}
