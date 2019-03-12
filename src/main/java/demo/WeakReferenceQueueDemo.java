package demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author huishen
 * @date 2019-01-28 10:13
 *
 * 创建软引用、弱引用或者虚引用对象时，可以为其关联一个引用队列ReferenceQueue
 * 当软引用、弱引用或者虚引用关联的对象被回收的时候，JVM会将该软引用、弱引用或者虚引用对象添加到ReferenceQueue中。
 */
public class WeakReferenceQueueDemo {

    private void printReferenceQueue(ReferenceQueue<Object> referenceQueue) {
        WeakEntry sv;
        while ((sv = (WeakEntry) referenceQueue.poll()) != null) {
            System.out.println("引用队列中元素的key：" + sv.key);
        }
    }

    private static class WeakEntry extends WeakReference<Object> {
        private Object key;

        WeakEntry(Object key, Object value, ReferenceQueue<Object> referenceQueue) {
            //调用父类的构造函数，并传入需要进行关联的引用队列
            super(value, referenceQueue);
            this.key = key;
        }
    }

    public static void main(String[] args) {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        User user = new User("xuliugen", "123456");
        WeakReferenceQueueDemo.WeakEntry weakEntry = new WeakReferenceQueueDemo.WeakEntry("654321", user, referenceQueue);
        System.out.println("还没被回收之前的数据：" + weakEntry.get());

        user = null;
        System.gc(); //强制执行GC
        System.runFinalization();

        System.out.println("已经被回收之后的数据：" + weakEntry.get());
        new WeakReferenceQueueDemo().printReferenceQueue(referenceQueue);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String userName;
        private String userPwd;
    }

}
