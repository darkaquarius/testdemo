package demo;

import org.junit.Test;

public class StringBuilderDemo {

    @Test
    public void test01() {
        String domain = null;
        StringBuilder sb = new StringBuilder("a")
                .append("b")
                .append("c")
                .append(domain);
        String s = sb.toString();
        System.out.println(s);

    }

}
