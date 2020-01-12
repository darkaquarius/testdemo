package demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huishen on 17/4/27.
 */
public class SystemDemo {

    @Test
    public void testGetenv() {
        Map<String, String> getenv = System.getenv();
    }

    @Test
    public void test() {
        Properties properties = System.getProperties();
    }

    @Test
    public void test2() {
        long l = System.currentTimeMillis();
        System.out.println(l);
    }

    /**
     * arraycopy()其实是浅拷贝
     */
    @Test
    @SuppressWarnings("Duplicates")
    public void test3() {
        final int length = 5;
        Person[] src = generatePersonArray(length);

        Person[] dest = new Person[length];
        System.arraycopy(src, 0, dest, 0, length);

        // 修改src中的值，desc也会变化
        Person p0 = src[0];
        p0.setNo(10000);
        p0.getJob().setYear(10000);
    }

    /**
     * 深拷贝
     */
    @Test
    @SuppressWarnings("Duplicates")
    public void test3_1() throws CloneNotSupportedException {
        final int length = 5;
        Person[] src = generatePersonArray(length);
        Person[] dest = new Person[length];
        for (int i = 0; i < length; i++) {
            // Person
            Person person = (Person) src[i].clone();
            dest[i] = person;
        }

        // 此时，修改src中的值，desc不会变化
        Person p0 = src[0];
        p0.setNo(10000);
        p0.getJob().setYear(10000);
    }

    private Person[] generatePersonArray(int length) {
        Person[] src = new Person[length];
        for (int i = 0; i < length; i++) {
            Person person = new Person(i, String.valueOf(i), i);
            Person.Job job = new Person.Job(String.valueOf(i), i);
            person.setJob(job);
            src[i] = person;
        }
        return src;
    }

}
