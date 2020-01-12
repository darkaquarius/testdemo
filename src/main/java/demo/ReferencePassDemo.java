package demo;

/**
 * @author huishen
 * @date 2019-07-24 23:52
 *
 * 值传递和引用传递
 *
 */
public class ReferencePassDemo {

    public static void main(String[] args) {
        ReferencePassDemo referencePassDemo = new ReferencePassDemo();

        int num = 10;
        referencePassDemo.intFunc(num);
        System.out.println(num);  // 10  值传递

        String str = "origin";
        referencePassDemo.stringFunc(str);
        System.out.println(str);    // "origin"  值传递

        int[] array = {1, 2, 3};
        referencePassDemo.arrayFunc(array);
        System.out.println(array);    // 10, 2, 3  引用传递

        Person person = new Person(0, "shen", 29);
        referencePassDemo.objectFunc(person);
        System.out.println(person);    // 1, hui, 20  引用传递
    }

    private void intFunc(int num) {
        num = 100;
    }

    private void stringFunc(String str) {
        str = "changed";  // 这里会开辟新的内存空间，改变了str指向的内存地址
    }

    public void arrayFunc(int[] array) {
        array[0] = 10;
    }

    public void objectFunc(Person person) {
        person.setNo(1);
        person.setName("hui");
        person.setAge(20);
    }

}
