package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice06_07 {

    public static void main(String arrgs[]){
        MySubClass mySubClass = new MySubClass(10);
        System.out.println(mySubClass);
    }
}

class MyClass{
    int value;
}

class MySubClass extends MyClass{
    public MySubClass(int value){
        this.value = value;
    }
}
