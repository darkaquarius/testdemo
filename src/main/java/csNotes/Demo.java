package csNotes;

import org.junit.Test;

import java.util.LinkedList;

/**
 *
 * https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/%E5%89%91%E6%8C%87%20Offer%20%E9%A2%98%E8%A7%A3%20-%20%E7%9B%AE%E5%BD%95.md\
 * 剑指 Offer 题解 - 目录.md
 *
 * @author huishen
 * @date 2019-04-12 10:10
 */
public class Demo {

    /**
     * 将一个字符串中的空格替换成 "%20"。
     */
    public String replaceSpace(StringBuilder str) {
        int P1 = str.length() - 1;
        for (int i = 0; i <= P1; i++)
            if (str.charAt(i) == ' ')
                str.append("  ");

        int P2 = str.length() - 1;
        while (P1 >= 0 && P2 > P1) {
            char c = str.charAt(P1--);
            if (c == ' ') {
                str.setCharAt(P2--, '0');
                str.setCharAt(P2--, '2');
                str.setCharAt(P2--, '%');
            } else {
                str.setCharAt(P2--, c);
            }
        }
        return str.toString();
    }

    @Test
    public void test1() {
        StringBuilder sb = new StringBuilder("A A B");
        String ret = replaceSpace(sb);
    }

    @Test
    public void test2() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        Integer integer = list.get(0);
    }

}
