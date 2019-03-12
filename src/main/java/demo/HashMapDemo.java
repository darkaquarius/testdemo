package demo;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huishen
 * @date 2019-02-26 16:20
 * <p>
 * 尽快整理这么多的HashMapDemo
 */

@SuppressWarnings("Duplicates")
public class HashMapDemo {

    /**
     * k9在k1的后面
     */
    @Test
    public void test0() {
        HashMap map = new HashMap(8);
        map.put("k1", "v1");
        map.put("k6", "v6");
        map.put("k8", "v8");
        map.put("k9", "v9");
    }

    /**
     * 这三个方法都是复制一个新的map对象相关的方法。
     * <p>
     * 复制构造函数
     */
    @Test
    public void test1() {
        HashMap<String, String> map1 = new HashMap<>(16);
        map1.put("key1", "value1");
        map1.put("key2", "value2");
        map1.put("key3", "value3");

        HashMap<String, String> map2 = new HashMap<>(map1);

        map1.put("key1", "valueX");
        map2.put("key1", "value11");
    }

    /**
     * 这三个方法都是复制一个新的map对象相关的方法。
     * <p>
     * clone()方法，由于HashMap实现了Cloneable接口，可以用Object类的clone()方法
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testClone() {
        HashMap<String, String> map1 = new HashMap<>(4);
        map1.put("key1", "value1");
        map1.put("key2", "value2");
        map1.put("key3", "value3");

        HashMap<String, String> map2 = (HashMap<String, String>) map1.clone();

        map1.put("key1", "valueX");

        map2.put("key1", "value11");
    }

    /**
     * 这三个方法都是复制一个新的map对象相关的方法。
     * <p>
     * copyOf
     * <p>
     * copyOf()生成的对象是不能修改的
     */
    @Test
    public void testCopyOf() {
        HashMap<String, String> map1 = new HashMap<>(4);
        map1.put("key1", "value1");
        map1.put("key2", "value2");
        map1.put("key3", "value3");

        Map<String, String> map2 = Map.copyOf(map1);

        map1.put("key1", "valueX");

        // UnsupportedOperationException
        // map2.put("key1", "value11");
    }


    /**
     * keySet(), entrySet(), values()里面都没有存数据，都是在调用迭代器的时候，将HashMap中的table数据输出来
     * 以keySet()为例：
     * 每次调用keySet()，返回的都是HashMap中的keySet属性，也即每次返回的都是同一个实例
     * <p>
     * keySet()返回的keySet只能做有限的操作，即：不能增，能删，不能改，能查，能清空
     * entrySet(), values()也是一样的。
     */
    @Test
    public void testKeySet() {
        HashMap<String, String> map = new HashMap<>(4);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Set<String> keySet1 = map.keySet();
        map.put("key4", "value4");
        Set<String> keySet2 = map.keySet();

        // keySet1 == keySet2
        System.out.println("keySet1 == keySet2: " + (keySet1 == keySet2));

        // keySet1, keySet2中的key1也删除了
        map.remove("key1");

        // UnsupportedOperationException
        try {
            keySet1.add("key5");
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        // ok!  map中也删除了对应的数据
        keySet1.remove("key2");

        // ok!  map中也清空了
        keySet1.clear();
    }

    @Test
    public void testEntrySet() {
        HashMap<String, String> map = new HashMap<>(4);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Set<Map.Entry<String, String>> entrySet1 = map.entrySet();
        map.put("key4", "value4");
        Set<Map.Entry<String, String>> entrySet2 = map.entrySet();

        // entrySet1 == entrySet2
        System.out.println("entrySet1 == entrySet2: " + (entrySet1 == entrySet2));

        // entrySet1, entrySet2中的key1也删除了
        map.remove("key1");

        // UnsupportedOperationException
        // 这里因为HashMap.Node不是public，无法访问，只能用Map.Entry的子类ImmutablePair代替
        try {
            entrySet1.add(new ImmutablePair<>("key2", "value2"));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        // ok!  map中也删除了对应的数据
        // 这里因为HashMap.Node不是public，无法访问，只能用Map.Entry的子类ImmutablePair代替
        entrySet1.remove(new ImmutablePair<>("key2", "value2"));

        // ok!  map中也清空了
        // 这里因为HashMap.Node不是public，无法访问，只能用Map.Entry的子类ImmutablePair代替
        entrySet1.clear();
    }

    @Test
    public void testValues() {
        HashMap<String, String> map = new HashMap<>(4);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Collection<String> values1 = map.values();
        map.put("key4", "value4");
        Collection<String> values2 = map.values();

        // values1 == values2
        System.out.println("values1 == values2: " + (values1 == values2));

        map.remove("key1");

        // UnsupportedOperationException
        try {
            values1.add("value5");
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        // ok!  map中也删除了对应的数据
        values1.remove("value2");

        // ok!  map中也清空了
        values1.clear();
    }

    /**
     * 如果在使用迭代器的过程中有其它线程修改了HashMap，将抛出ConcurrentModificationException，即fail-fast策略
     * 实现方式是通过比较modCount与expectedModCount
     * 例外：由于使用put()方法修改一个已经存在的key的值的时候，是不会改变modCount的值的，所以在使用迭代器的时候，修改一个已经存在的key的值是合法的。
     * <p>
     * 解决方法1：
     * 迭代器本身提供了remove()方法，这个方法可以在使用迭代器的时候，安全地做删除操作，因为remove()方法里面会修改expectedModCount的值。
     * 但是迭代器本身没有提供其他诸如add()等方法，因为KeySet, EntrySet, Values这些内部类本身也不提供add()方法。
     * 解决方法2：
     * 将HashMap改成ConcurrentHashMap。见下面的单测
     *
     */
    @Test
    public void testIterator() {
        HashMap<String, String> map = new HashMap<>() {
            {
                put("k1", "v1");
                put("k2", "v2");
                put("k3", "v3");
            }
        };

        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        // error!
        for (Map.Entry<String, String> entry : entrySet) {
            if ("v1".equals(entry.getValue())) {
                // 方法1: error! ConcurrentModificationException
                map.remove(entry.getKey());

                // 方法2: error! ConcurrentModificationException
                entrySet.remove(ImmutablePair.of("k1", "v1"));
            }

            // 这里修改k1, k2, k3的值是可以的，但是不能添加或者
            map.put("k1", "vX");
        }

        // 这里只能使用迭代器本身提供的remove()方法，他会修改expectedModCount的值。
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if ("v1".equals(next.getValue())) {
                // 方法3, ok!!!
                iterator.remove();
                iterator.forEachRemaining(System.out::println);
            }
        }
    }

    /**
     * ConcurrentHashMap在使用迭代器的时候，可以允许同时修改原本ConcurrentHashMap的操作。
     */
    @Test
    public void testConcurrentHashMapIterator() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>() {
            {
                put("k1", "v1");
                put("k2", "v2");
                put("k3", "v3");
            }
        };

        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {
            if ("k1".equals(entry.getKey())) {
                map.remove("k1");
                map.put("k4", "v4");
            }
        }
    }

    /**
     * equals()相等，hashCode()一定相等
     * equals()不等，hashCode()不一定不等
     * <p>
     * hashCode方法 默认  是将对象的存储地址进行映射
     * <p>
     * 修改equals()，也要同时修改hashCode()，不然有可能equals()相等，hashCode()不等
     */
    @Test
    public void test10() {
        People p1 = new People("Jack", 12);
        System.out.println(p1.hashCode());

        HashMap<People, Integer> hashMap = new HashMap<>();
        hashMap.put(p1, 1);

        // 这里修改了p1里面的值，hashCode()变化了，取不出值
        p1.setAge(13);
        System.out.println(p1.hashCode());

        System.out.println(hashMap.get(p1));
    }

    public static class People {
        private String name;
        private int age;

        public People(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public int hashCode() {
            return name.hashCode() * 37 + age;
        }

        @Override
        public boolean equals(Object obj) {
            return this.name.equals(((People) obj).name) && this.age == ((People) obj).age;
        }
    }

}
