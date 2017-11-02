package demo;

import lombok.Data;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by huishen on 17/10/31.
 *
 */

public class TransientDemo {

    /**
     * 被transient修饰的属性，不能被序列化
     * 当对象被反序列化后，没有调用构造方法
     */
    public void main(String[] args) throws IOException {
        // args[0]: /Users/huishen/git/loan-backend/loan-dao/target/classes/com/loan/backend/dao/mapper/OfflineUserMapper.class

        // result
        // ClassLib(InputStream) called
        // Minor version number: 0
        // Major version number: 52
        // java.io.FileInputStream@2eafffde
        //
        // Minor version number: 0
        // Major version number: 52
        // null
        if (args.length != 1) {
            System.err.println("usage: java TransDemo classfile");
            return;
        }
        ClassLib cl = new ClassLib(new FileInputStream(args[0]));
        System.out.printf("Minor version number: %d%n", cl.getMinorVer());
        System.out.printf("Major version number: %d%n", cl.getMajorVer());
        cl.showIS();

        try (FileOutputStream fos = new FileOutputStream("x.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(cl);
        }

        cl = null;

        try (FileInputStream fis = new FileInputStream("x.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            System.out.println();
            cl = (ClassLib) ois.readObject();
            System.out.printf("Minor version number: %d%n", cl.getMinorVer());
            System.out.printf("Major version number: %d%n", cl.getMajorVer());
            cl.showIS();
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }

    /**
     * 类变量不能被序列化
     * 实例变量z加上了transient，不能被序列化
     */
    @Test
    public void test() throws IOException {
        // result
        // Foo construction
        // w: 1
        // x: 2
        // y: 3
        // z: 4
        //
        // w: 1
        // x: 2
        // y: 3
        // z: 0
        Foo foo = new Foo();
        System.out.printf("w: %d%n", Foo.w);
        System.out.printf("x: %d%n", Foo.x);
        System.out.printf("y: %d%n", foo.y);
        System.out.printf("z: %d%n", foo.z);
        try (FileOutputStream fos = new FileOutputStream("x.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(foo);
        }

        foo = null;

        try (FileInputStream fis = new FileInputStream("x.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            System.out.println();
            foo = (Foo) ois.readObject();
            System.out.printf("w: %d%n", Foo.w);
            System.out.printf("x: %d%n", Foo.x);
            System.out.printf("y: %d%n", foo.y);
            System.out.printf("z: %d%n", foo.z);
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }
}

class ClassLib implements Serializable {
    // 如果此变量不是transient的话，当反序列化x.er时候，会抛出java.io.NotSerializableException
    // 原因是InputStream没有实现Serializable接口
    private transient InputStream is;

    private int majorVer;
    private int minorVer;

    ClassLib(InputStream is) throws IOException {
        System.out.println("ClassLib(InputStream) called");
        this.is = is;
        DataInputStream dis;
        if (is instanceof DataInputStream) {
            dis = (DataInputStream) is;

        } else {
            dis = new DataInputStream(is);
        }
        if (dis.readInt() != 0xcafebabe) {
            throw new IOException("not a .class file");
        }
        minorVer = dis.readShort();
        majorVer = dis.readShort();
    }

    int getMajorVer() {
        return majorVer;
    }

    int getMinorVer() {
        return minorVer;
    }

    void showIS() {
        System.out.println(is);
    }
}

@Data
class Foo implements Serializable {
    public static int w = 1;
    public static int x = 2;

    public int y = 3;
    public transient int z = 4;

    public Foo() {
        System.out.println("Foo construction");
    }
}
