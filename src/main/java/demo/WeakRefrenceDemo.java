package demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.WeakReference;

/**
 * @author huishen
 * @date 2019-01-28 10:09
 */
public class WeakRefrenceDemo {

    public static void main(String[] args) {
        User user = new User("hello", "123");
        WeakReference<User> userWeakReference = new WeakReference<>(user);
        System.out.println(userWeakReference.get());
        user = null;
        //另一种方式触发GC，强制执行GC
        // 也可以用visualvm去强制gc
        System.gc();
        System.runFinalization();
        System.out.println(userWeakReference.get());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String userName;
        private String userPwd;
    }

}
