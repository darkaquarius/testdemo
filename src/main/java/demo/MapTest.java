package demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huishen on 17/2/10.
 *
 */
public class MapTest {


    @Test
    public void test(){
        Map<Integer, Person> map = new ConcurrentHashMap();
        map.put(1, new Person(1,"zhang",20));
        map.put(2, new Person(2,"zhang",30));

        //两次取同一个key,返回的value是同一个对象
        Person person1 = map.get(1);
        System.out.println(person1);
        System.out.println();
        Person person2 = map.get(2);
        System.out.println(person2);
        System.out.println();
        Person person3 = map.get(1);
        System.out.println(person3);

        Person person = map.get(3);
        System.out.println(person);
    }

    @Test
    public void test2(){
        Map<SimpApp, Integer> simpAppMap = new HashMap<>();
        simpAppMap.put(SimpApp.builder().appId("1").appName("zhang").build(), 10);
        simpAppMap.put(SimpApp.builder().appId("2").appName("wang").build(), 20);
        simpAppMap.put(SimpApp.builder().appId(null).appName(null).build(), 3);

        System.out.println(simpAppMap.get(SimpApp.builder().appId("1").build()));
        System.out.println(simpAppMap.get(SimpApp.builder().appId("2").build()));
        System.out.println(simpAppMap.get(SimpApp.builder().appId(null).build()));
    }

    @Test
    public void testKeySet() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        Set<String> keySet1 = map.keySet();

        map.put("5", "e");
        Set<String> keySet2 = map.keySet();

        //比较两个keySet
        //每次调用keySet()方法,返回的是同一个实例!!!

        System.out.println(keySet1);
        System.out.println(keySet2);

    }

}
