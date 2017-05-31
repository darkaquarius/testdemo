package practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huishen on 16/12/24.
 */
public class Practice02_06 {

    //内外两圈循环,注意哪些变量在下一次循环的时候要初始化。
    //内外两圈最好用不同的变量循环,不然容易出问题
    public List<Map<String, Integer>> func(){
        int a=0, b=0, c=0;
        int SUM_MONEY = 100;
        List<Map<String, Integer>> list = new ArrayList();
        for(a = 0; a < SUM_MONEY / 3; a++){
            int money = SUM_MONEY - 3 * a;
            for(b = 0; b < money / 2; b++){
                int money2 = money - 2 * b;
                c = money2 * 3;
                if(100 == (a + b + c)){
                    Map<String, Integer> map = new HashMap<>();
                    map.put("a", a);
                    map.put("b", b);
                    map.put("c", c);
                    list.add(map);
                }else{
                    continue;
                }

            }

        }

        return list;
    }

    public static void main(String args[]){
        Practice02_06 practice2_06 = new Practice02_06();
        List<Map<String, Integer>> func = practice2_06.func();
        System.out.println(func);
    }
}
