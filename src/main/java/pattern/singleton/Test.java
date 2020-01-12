package pattern.singleton;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author huishen
 * @date 2019-08-01 14:46
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // // enum实现单例模式
        // Thread thread0 = new Thread(() -> {
        //     EnumSingleton instance = EnumSingleton.getInstance();
        //     System.out.println(Thread.currentThread().getName() + "  " + instance);
        // });
        //
        // Thread thread1 = new Thread(() -> {
        //     EnumSingleton instance = EnumSingleton.getInstance();
        //     System.out.println(Thread.currentThread().getName() + "  " + instance);
        // });
        //
        // thread0.start();
        // thread1.start();
        //
        // thread0.join();
        // thread1.join();

        // // EnumSingleton在序列化，再反序列化之后，不会创建新的对象
        // EnumSingleton instance = EnumSingleton.getInstance();
        // instance.setData(new Object());
        // ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton_file"));
        // oos.writeObject(instance);
        //
        // ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singleton_file"));
        // EnumSingleton newInstance = (EnumSingleton) ois.readObject();  // 通过valueOf()方法获得对象，没有用反射
        //
        // System.out.println(instance);
        // System.out.println(newInstance);
        // System.out.println(instance == newInstance);
        // System.out.println();
        // System.out.println(instance.getData());
        // System.out.println(newInstance.getData());
        // System.out.println(instance.getData() == newInstance.getData());

        // 反射也不能破坏EnumSingleton
        EnumSingleton instance = EnumSingleton.getInstance();
        Class<EnumSingleton> objectClass = EnumSingleton.class;
        // NoSuchMethodException, Enum有且只有一个Enum(String, int)构造方法, 没有无参构造方法
        // Constructor<EnumSingleton> constructor = objectClass.getDeclaredConstructor();
        Constructor<EnumSingleton> constructor = objectClass.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        // 抛出异常: Cannot reflectively create enum objects
        EnumSingleton newInstance = constructor.newInstance("shen", 10);

        System.out.println(instance);
        System.out.println(newInstance);
        System.out.println(instance == newInstance);

    }

}
