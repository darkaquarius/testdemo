package demo;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huishen on 16/12/18.
 */
public class StringDemo {

    //替换字符
    @Test
    public void test1(){
        String str = "a b c";
        str = str.replace(' ', 'b');

        System.out.println(str);
    }

    //用正则替换字符串
    @Test
    public void test2(){
        String str = "a b c";
        str = str.replaceAll(" ", "%20");
        System.out.println(str);
    }

    //String中, 查询那个字符串在什么位置(可以选择从什么位置开始)
    @Test
    public void test3(){
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
        String str2 = str.substring(i1 + 12, i2-1);
        System.out.println(str2);
    }

    //在String中没有查到对应的字符串的结果
    @Test
    public void test4(){
        String str = "hello world";
        int shen = str.indexOf("shen");
        System.out.println(shen);
    }

    @Test
    public void test5(){
        String str = null;
        for(int i=0; i<1; i++){
            str = "hello world";
        }
        System.out.println(str);
    }

    @Test
    public void test6(){
        String url = "https://itunes.apple.com/us/genre/id6014";
        int idIndex = url.indexOf("id");
        String genreId = url.substring(idIndex+2);
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
    public void test8(){
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
        for (int i = 0; i< split.length; i++) {
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

}
