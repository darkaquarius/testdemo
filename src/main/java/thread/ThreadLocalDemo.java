package thread;

import java.util.Random;

/**
 * Created by huishen on 17/2/27.
 * ThreadLocal, 线程局部变量
 * sleep前后，age属性中的值不变
 */
public class ThreadLocalDemo implements Runnable {
    /**
     * 创建线程局部变量studentLocal，在后面你会发现用来保存Student对象
     */
    private final static ThreadLocal<Student> studentLocal = new ThreadLocal<>();

    private final static ThreadLocal<Teacher> teacherLcoal = new ThreadLocal<>();

    @Override
    public void run() {
        accessStudent();
    }

    /**
     * 示例业务方法，用来测试
     */
    public void accessStudent() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " is running!");

        // 1.产生一个随机数并打印
        int age = new Random().nextInt(100);

        // 2.获取一个Student对象，并将随机数age插入到对象属性中
        getStudent().setAge(age);
        getTeacher().setName("zhangsan" + age);
        System.out.println("thread " + currentThreadName + " first read age is:" + getStudent().getAge());
        System.out.println("thread " + currentThreadName + " teacher's name is:" + getTeacher().getName());
        // 3.sleep
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // 4.获取age,看看有没有变化
        System.out.println("thread " + currentThreadName + " second read age is:" + getStudent().getAge());
        System.out.println("thread " + currentThreadName + " teacher's name is:" + getTeacher().getName());


    }

    private Student getStudent() {
        //获取本地线程变量并强制转换为Student类型
        Student student = studentLocal.get();
        //线程首次执行此方法的时候，studentLocal.get()肯定为null
        if (student == null) {
            //创建一个Student对象，并保存到本地线程变量studentLocal中
            student = new Student();
            studentLocal.set(student);
        }
        return student;
    }

    private Teacher getTeacher() {
        Teacher teacher = teacherLcoal.get();
        if (teacher == null) {
            teacher = new Teacher();
            teacherLcoal.set(teacher);
        }
        return teacher;
    }

    public static void main(String[] agrs) {
        ThreadLocalDemo td = new ThreadLocalDemo();
        Thread t1 = new Thread(td, "a");
        Thread t2 = new Thread(td, "b");
        Thread t3 = new Thread(td, "c");
        Thread t4 = new Thread(td, "d");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static class Teacher {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Student {
        private int age = 0;   //年龄

        public int getAge() {
            return this.age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
