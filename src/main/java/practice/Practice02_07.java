package practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huishen on 16/12/24.
 */
public class Practice02_07 {

    public List<Map<String, Integer>> func(){
        int STONES = 36;
        int men;
        int women;
        int children;
        List<Map<String, Integer>> list = new ArrayList<>();
        for(int i = 0; i < STONES / 4; i++){
            int num = STONES - 4 * i;
            for(int j = 0; j < num / 3; j++){
                int num2 = num - 3 * j;
                int k = num2 * 2;
                if(36 == (i + j + k)){
                    Map<String, Integer> map = new HashMap<>();
                    map.put("men", i);
                    map.put("women", j);
                    map.put("children", k);
                    list.add(map);
                }
            }
        }
        return list;
    }

    public static void main(String args[]){
        Practice02_07 practice2_07 = new Practice02_07();
        List<Map<String, Integer>> list = practice2_07.func();
        System.out.println(list);
    }
}
