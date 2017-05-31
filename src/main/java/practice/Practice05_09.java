package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice05_09 {

    public static void main(String args[]){
        A a = new A();
        a.value = 10;
        change(a);
        System.out.println(a.value);

    }

    public static void change(A a){
        a.value++;
    }
}

class A{
    int value;
}
