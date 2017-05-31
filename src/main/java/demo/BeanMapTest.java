package demo;

import org.apache.commons.beanutils.BeanMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by huishen on 17/2/6.
 */
public class BeanMapTest {

    @Test
    public void test1(){
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "wang", 18));
        persons.add(new Person(2, "li", 20));

        Person person1 = persons.get(0);
        System.out.println(person1.getNo());

        List<Map<Object, Object>> maps = new ArrayList<>();

        for(Person person : persons){
            //
            BeanMap beanMap = new BeanMap(person);
            maps.add(beanMap);
        }

        System.out.println(maps);
    }

    @Test
    public void test2(){
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "wang", 18));
        persons.add(new Person(2, "li", 20));

        Person person1 = persons.get(0);
        System.out.println(person1.getNo());

        List<BeanMap> mapList = persons.stream().map(BeanMap::new).collect(Collectors.toList());

        for(BeanMap b : mapList){
            System.out.println(b.get("no"));
            System.out.println(b.get("name"));
        }

    }

}
