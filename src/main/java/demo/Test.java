package demo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huishen
 * @date 18/4/2 上午11:07
 */
public class Test {

    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<String, String>(){{put("1", "1");}};
        Map<String, String> map2 = new HashMap<String, String>(){{put("2", "2");}};
        Map<String, String> map3 = new HashMap<String, String>(){{put("3", "3");}};

        String str = "4";
        String s = map1.get(str) != null ? map1.get(str) : map2.get(str) != null ? map2.get(str) : map3.get(str);
        System.out.println(s);

    }

}
