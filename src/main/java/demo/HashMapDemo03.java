package demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huishen on 17/6/16.
 *
 */

public class HashMapDemo03 {

    public static void main(String[] args) {

        Map<MapKey,String> map = new HashMap<>();
        //第一阶段
        for (int i = 0; i < 6; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        System.out.println();

        //第二阶段
        for (int i = 0; i < 10; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        System.out.println();

        //第三阶段
        for (int i = 0; i < 50; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        System.out.println();

        //第四阶段
        map.put(new MapKey("X"), "B");
        map.put(new MapKey("Y"), "B");
        map.put(new MapKey("Z"), "B");

        System.out.println(map);

    }


    public static class MapKey {
        private static final String REG = "[0-9]+";
        private String key;

        public MapKey(String key) {
            this.key = key;
        }

        @Override
        public int hashCode() {
            if (key.matches(REG))
                return 1;
            else
                return 2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MapKey mapKey = (MapKey) o;

            return !(key != null ? !key.equals(mapKey.key) : mapKey.key != null);

        }

        @Override
        public String toString() {
            return key;
        }
    }

}
