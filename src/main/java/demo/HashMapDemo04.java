package demo;

import java.util.HashMap;

/**
 * Created by huishen on 17/12/6.
 *
 */
public class HashMapDemo04 {

    /**
     * equals()相等，hashCode()一定相等
     * equals()不等，hashCode()不一定不等
     *
     *
     * hashCode方法 默认  是将对象的存储地址进行映射
     *
     * 修改equals()，也要同时修改hashCode()，不然有可能equals()相等，hashCode()不等
     *
     */
    public static void main(String[] args) {

        People p1 = new People("Jack", 12);
        System.out.println(p1.hashCode());

        HashMap<People, Integer> hashMap = new HashMap<People, Integer>();
        hashMap.put(p1, 1);

        // 这里修改了p1里面的值，hashCode()变化了，取不出值
        p1.setAge(13);
        System.out.println(p1.hashCode());

        System.out.println(hashMap.get(p1));
    }


}

class People{
    private String name;
    private int age;

    public People(String name,int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return name.hashCode()*37+age;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return this.name.equals(((People)obj).name) && this.age== ((People)obj).age;
    }
}
