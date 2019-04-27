package demo;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by huishen on 16/12/18.
 */
public class StringDemo {

    //替换字符
    @Test
    public void test1() {
        String str = "a b c";
        str = str.replace(' ', 'b');

        System.out.println(str);
    }

    //用正则替换字符串
    @Test
    public void test2() {
        String str = "a b c";
        str = str.replaceAll(" ", "%20");
        System.out.println(str);
    }

    //String中, 查询那个字符串在什么位置(可以选择从什么位置开始)
    @Test
    public void test3() {
        String str = "\"iad\": {\n" +
            "                        \"impressionId\": \"90c35757-adb9-4503-a876-7d77577bf3d9\",\n" +
            "                        \"metadata\": \"CiQ5MGMzNTc1Ny1hZGI5LTQ1MDMtYTg3Ni03ZDc3NTc3YmYzZDkSBzk1ODIxMTUZAAAAAAAA8D8iA0NQQyoETVIxMTICUDE5AAAAAAAA8D9CBG51bGxJAAAAAAAA8D9SBzg5MTk5MDJZAAAAAAAA8D9hAAAAAAAA8D9qBGN1YmVyBzk1ODIxMTV6DDAuMzEwMDAwMDAwMIoBEDE1MDAwLjAwMDAwMDAwMDCSAQ41MDAuMDAwMDAwMDAwMJkBAAAA4MqQ7kE=\",\n" +
            "                        \"lineItem\": \"911793120\",\n" +
            "                        \"algoId\": \"0\",\n" +
            "                        \"privacy\": \"EjxDQU1TQlhwb1gwTk9HZzVwVDFNZ01UQXVNaTB4TkVNNU1pb0lDZ0kwT0JJQ1ZWTXFCd29DTlRNU0FUQT0aQmh0dHBzOi8vaWFkc2RrLmFwcGxlLmNvbS9hZHNlcnZlci8yLjIvc2VnbWVudC9wcml2YWN5cmVuZGVyLzEuMC9hZA==\",\n" +
            "                        \"format\": {\n" +
            "                            \"images\": false,\n" +
            "                            \"text\": \"description\",\n" +
            "                            \"userRating\": true,\n" +
            "                            \"icon\": \"regular\"\n" +
            "                        },\n" +
            "                        \"templateType\": \"regular\"\n" +
            "                    }";

        int i1 = str.indexOf("lineItem");
        int i2 = str.indexOf(",", i1);
        System.out.println(i1);
        String str2 = str.substring(i1 + 12, i2 - 1);
        System.out.println(str2);
    }

    //在String中没有查到对应的字符串的结果
    @Test
    public void test4() {
        String str = "hello world";
        int shen = str.indexOf("shen");
        System.out.println(shen);
    }

    @Test
    public void test5() {
        String str = null;
        for (int i = 0; i < 1; i++) {
            str = "hello world";
        }
        System.out.println(str);
    }

    @Test
    public void test6() {
        String url = "https://itunes.apple.com/us/genre/id6014";
        int idIndex = url.indexOf("id");
        String genreId = url.substring(idIndex + 2);
        System.out.println(genreId);
    }

    @Test
    public void test7() {
        String str = "\"hello\"";
        System.out.println(str);
        str.replaceAll("\"", "\"\"");
        System.out.println(str);
    }

    @Test
    public void test8() {
        String str = "hello\'hello";
        System.out.println(str);
        str = str.replaceAll("\'", "\\\\'");
        System.out.println(str);
    }

    @Test
    public void testReplaceAll() {
        StringBuilder sb = new StringBuilder();

        String str = "[\"成都的学生党 陈先生 今日借款 3000元 已到账\",\"平顶山的上班族 花女士 今日借款 4000元 已到账\",\"邵阳的学生党 康先生 今日借款 3000元 已到账\"]";
        String[] split = str.split(" ");
        for (int i = 0; i < split.length; i++) {
            if (i % 2 != 0)
                split[i] = "<font color='#ff0000'>".concat(split[i]).concat("</font>");
            sb.append(split[i]).append(" ");
        }
        String ret = sb.deleteCharAt(sb.length() - 1).toString();
        System.out.println(ret);
    }

    @Test
    public void testLength() {
        int length = "320482199002080115".length();
        Assert.assertEquals(length, 18);
    }

    @Test
    public void test10() {
        String oldStr = null;
        String newStr = "123";
        if (newStr.equals(oldStr)) {
            System.out.println(1);
        }
    }

    @Test
    public void test11() {
        String key = "loan:idfa:com.loan.yi";
        String[] split = key.split(":", 3);
        System.out.println(split[2]);
    }

    // 60406
    // 210719
    @Test
    public void test12() {
        long s = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            String strA = "a";
            String strB = "b";
            String strC = "c";
            String strD = "d";
            String strE = "e";
            String result = strA + strB + strC + strD + strE;
        }
        System.out.println("spend:" + (System.nanoTime() - s));
    }

    // 73752
    // 99660
    @Test
    public void test12_1() {
        long s = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            String strA = "a";
            String strB = "b";
            String strC = "c";
            String strD = "d";
            String strE = "e";
            String result = strA.concat(strB).concat(strC).concat(strD).concat(strE);
        }

        System.out.println("spend:" + (System.nanoTime() - s));
    }

    /**
     * test13(), test14(), test15(), test16() 分别比较了在循环条件下，
     * "+"操作符， concat()方法， StringBuffer,  StringBuilder  的性能差异
     */

    // 9623
    @Test
    public void test13() {
        String result = "";

        long s = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            result = result + "*";
        }
        System.out.println("spend: " + (System.currentTimeMillis() - s));
    }

    // 3716
    @Test
    public void test14() {
        String result = "";

        long s = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            result = result.concat("*");
        }
        System.out.println("spend: " + (System.currentTimeMillis() - s));
    }

    // 32
    @Test
    public void test15() {
        String result = "";

        StringBuffer sb = new StringBuffer();
        long s = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            sb.append("*");
        }
        result = sb.toString();
        System.out.println("spend: " + (System.currentTimeMillis() - s));
    }

    // 15
    @Test
    public void test16() {
        String result = "";

        StringBuilder sb = new StringBuilder();
        long s = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            sb.append("*");
        }
        result = sb.toString();
        System.out.println("spend: " + (System.currentTimeMillis() - s));
    }

    @Test
    public void test17() {
        String url = "tLTCBTC";
        String substring = url.substring(1, url.length());
    }

    /**
     * charAt
     */
    @Test
    public void testCharAt() {
        String str = "asdf";
        System.out.println(str.charAt(0));    // a
        System.out.println(str.charAt(1));    // s
        System.out.println(str.charAt(2));    // d
        System.out.println(str.charAt(3));    // f
    }


    /**
     * charAt, indexof
     */
    @Test
    public void test18() {
        String[] ret = findPrefixRange("abg");
        System.out.println(ret);
    }

    private String[] findPrefixRange(String prefix) {
        String VALID_CHARACTERS = "`abcdefghijklmnopqrstuvwxyz{";
        int posn = VALID_CHARACTERS.indexOf(prefix.charAt(prefix.length() - 1));
        char suffix = VALID_CHARACTERS.charAt(posn > 0 ? posn - 1 : 0);
        String start = prefix.substring(0, prefix.length() - 1) + suffix + '{';
        String end = prefix + '{';
        return new String[]{start, end};
    }

    @Test
    public void test19() {
        // a--97, b--98
        String str = new String(new char[]{(char) 97, (char) 98});
        System.out.println(str);
    }

    @Test
    public void test20() {
        String str = "http://images.diaoqianyaner.com.cn/img_appointment/20161205CK55HEWE.jpg";
        String[] split = str.split("/");
        System.out.println(split[4]);
    }

    /**
     * unicode转换
     * StringEscapeUtils.unescapeJava()方法可以传入有unicode编码或者没有unicode编码的字符串，也能传null
     */
    @Test
    public void test21() {
        String unicodeStr = "\u540c\u7a0b\u827a\u9f99\u9152\u5e97\u673a\u7968\u706b\u8f66";
        // String unicodeStr = "hello world";
        String str = StringEscapeUtils.unescapeJava(unicodeStr);
        System.out.println(str);
    }

    @Test
    public void main() {
        String day = "2018/10/10";
        String replace = day.replace("/", "");
        Integer ret = Integer.valueOf(replace);
        System.out.println(ret);
    }

    @Test
    public void test22() {
        List<String> list = Arrays.asList("vivo", "huawei");
        String collect = list.stream().map(str -> String.format("\'%s\'", str)).collect(Collectors.joining(","));
        System.out.println(collect);
    }

    @Test
    public void testIntern1() {
        String s = new String("1");
        s = s.intern();
        String s2 = "1";
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        s3 = s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
    }

    /**
     * s0==s1==s2
     */
    @Test
    public void testIntern4() {
        String s0 = "kvill";
        String s1 = "kvill";
        String s2 = "kv" + "ill";
        System.out.println(s0 == s1);
        System.out.println(s0 == s2);
    }

    @Test
    public void testIntern5() {
        String s0 = "kvill";
        String s1 = new String("kvill");
        String s2 = "kv" + new String("ill");
        System.out.println(s0 == s1);
        System.out.println(s0 == s2);
        System.out.println(s1 == s2);
    }

    @Test
    public void testIntern6() {
        String s0 = "kvill";
        String s1 = new String("kvill");
        String s2 = new String("kvill");
        System.out.println(s0 == s1);
        System.out.println("**********");

        // 虽然执行了s1.intern(),但它的返回值没有赋给s1
        s1.intern();

        // 把常量池中“kvill”的引用赋给s2
        s2 = s2.intern();
        System.out.println(s0 == s1);
        System.out.println(s0 == s1.intern());
        System.out.println(s0 == s2);
    }


}
