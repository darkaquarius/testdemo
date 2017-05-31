package demo;

/**
 * Created by huishen on 17/2/4.
 */
public class MySubClass extends MyClass{
    public MySubClass(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static void main(String[] args){
        MySubClass mySubClass = new MySubClass(10);
        System.out.println(mySubClass.getValue());
    }
}
