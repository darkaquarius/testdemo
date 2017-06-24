package demo;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by huishen on 17/6/16.
 *
 */
public class HashMapDemo02 {

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap =  new HashMap<>();
        hashMap.put("a", 0);
        hashMap.put("b", 1);
        hashMap.put("c", 2);
        hashMap.put("d", 3);
        hashMap.put("e", 4);
        hashMap.put("f", 5);
        hashMap.put("g", 6);
        hashMap.put("h", 7);
        hashMap.put("i", 8);
        hashMap.put("j", 9);
        // q和a在capacity是16的hashCode bucket中的位置一致
        hashMap.put("q", 10);
        hashMap.put("A", 11);
        // 这里resize()
        hashMap.put("Q", 12);
        hashMap.put("n", 13);
        hashMap.put("o", 14);
        hashMap.put("p", 15);
        hashMap.put("q", 16);
        hashMap.put("r", 17);
        hashMap.put("s", 18);
    }

    // 找出hash值与15按位相等的字母
    // 当容量一定是2^n时，h & (length - 1) == h % length
    @Test
    public void testHashCode() {
        int hashCode = "a".hashCode();   // 97
        // 16 - 1 = 15
        int ret = hashCode & 15;    // 1

        int ret2;

        //从A到z
        for (int i = 65; i < 122; i++) {
            ret2 = i & 15;
            if (ret2 == ret) {
                System.out.println(i);
            }
        }

    }

    @Test
    public void test() {
        int hashCode1 = "a".hashCode();
        int hashCode2 = "q".hashCode();
        int hashCode3 = "A".hashCode();
        int hashCode4 = "Q".hashCode();
        int ret1 = hashCode1 & 16;
        int ret2 = hashCode2 & 16;
        int ret3 = hashCode3 & 16;
        int ret4 = hashCode4 & 16;
    }

}
