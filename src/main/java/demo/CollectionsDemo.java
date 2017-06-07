package demo;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by huishen on 17/6/6.
 *
 */
public class CollectionsDemo {

    @Test
    public void testUnmodifiableSet() {
        Set<String> names = new HashSet<>();
        names.add("zhangsan");
        names.add("lisi");
        names.add("wangwu");

        //unmodifiableSet
        Set<String> ret = Collections.unmodifiableSet(names);

        names.add("baichi");

        // java.lang.UnsupportedOperationException
        // 这里用UnmodifiableSet类型，也引用了names对象
        // UnmodifiableSet类型的add()方法会报错
        // 但是，可以通过更改names，来更改ret，因为它们引用的是用一个对象
        // ret.add("baichi,too");

        for (String str : ret) {
            System.out.println(str);
        }
    }

}
