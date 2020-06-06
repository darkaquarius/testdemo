package demo;

import org.junit.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class TreeMapDemo {

    @Test
    public void test1() {
        ApiComparator apiComparator = new ApiComparator();
        TreeMap<Pattern, String> treeMap = new TreeMap<>(apiComparator);
        Pattern pattern1 = Pattern.compile("/business-marketing-page-pool-provider/*");
        Pattern pattern2 = Pattern.compile("/business-marketing-page-pool-provider/page/activity/*");

        treeMap.put(pattern1, "pattern1");
        treeMap.put(pattern2, "pattern2");

        Set<Map.Entry<Pattern, String>> entrySet = treeMap.entrySet();

        for (Map.Entry<Pattern, String> entry : entrySet) {
            System.out.println(entry);
        }
    }

    private class ApiComparator implements Comparator<Pattern> {

        @Override
        public int compare(Pattern o1, Pattern o2) {
            int diff = o2.pattern().length() - o1.pattern().length();
            if(diff != 0){
                return diff;
            }
            //长度相等
            return o2.hashCode() - o1.hashCode();
        }
    }

}
