package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by huishen on 17/3/7.
 *
 */

public class SetDemo {

    @Test
    public void test01(){
        Set<String> keywords = new HashSet<>();
        keywords.add("a");
        keywords.add("b");
        keywords.add("c");
        keywords.add("d");
        keywords.add("e");

        Set<String> relativeKeywords = new HashSet<>();
        relativeKeywords.add("a");
        relativeKeywords.add("b");
        relativeKeywords.add("h");
        relativeKeywords.add("I");
        relativeKeywords.add("j");
        relativeKeywords.add("k");

        int size = keywords.size();

        int relativeSize = relativeKeywords.size();
        relativeKeywords.removeAll(keywords);
        int restSize = relativeKeywords.size();
        if(((relativeSize - restSize) / (double)size) > 0.3){
            System.out.println("true");
        }
    }

    /**
     * 求交集
     */
    @Test
    public void test02() {
        Set<String> set1 = new HashSet<>();
        set1.add("1");
        set1.add("2");

        Set<String> set2 = new HashSet<>();
        set2.add("1");
        set2.add("3");

        boolean b = set1.retainAll(set2);
    }

    @Test
    public void test03() {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        String collect = set.stream().collect(Collectors.joining());
    }

    @Test
    public void testLinkedHashSet() {
        LinkedHashSet<String> set = new LinkedHashSet<String>(8);
        set.add("1");
        set.add("2");
        set.add("3");
        System.out.println(set);
        set.add("4");
        System.out.println(set);
        set.remove("3");
        set.add("3");
        System.out.println(set);
        set.remove("5");
        set.add("5");
        System.out.println(set);
    }
}
