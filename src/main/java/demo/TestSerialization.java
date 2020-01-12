package demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author huishen
 * @date 2019-07-31 23:16
 *
 * 在一个类中加入writeObject(ObjectOutputStream)和readObject(ObjectInputStream)方法，可以在序列化和反序列化时，自定义各个字段的行为
 *
 */
public class TestSerialization implements Serializable {
    private transient int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private void writeObject(ObjectOutputStream s) throws java.io.IOException {
        s.defaultWriteObject();
        s.writeObject(num);
        System.out.println("writeObject of " + this.getClass().getName());
    }

    private void readObject(ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        num = (Integer) s.readObject();
        System.out.println("readObject of " + this.getClass().getName());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TestSerialization test = new TestSerialization();
        test.setNum(10);
        System.out.println("序列化之前的值：" + test.getNum());

        // 写入
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("/Users/huishen/test.tmp"));
        outputStream.writeObject(test);

        // 读取
        ObjectInputStream oInputStream = new ObjectInputStream(new FileInputStream("/Users/huishen/test.tmp"));
        TestSerialization aTest = (TestSerialization) oInputStream.readObject();
        System.out.println("读取序列化后的值：" + aTest.getNum());
    }
}
