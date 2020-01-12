package thread;

import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author huishen
 * @date 2019-08-17 21:37
 */
public class ConcurrentSkipListMapDemo {

    @Test
    public void test1() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("1", "1");
        String value = map.get("1");
    }

}
