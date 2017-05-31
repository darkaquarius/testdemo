package demo;

/**
 * Created by huishen on 16/11/19.
 */
public class Singleton {


    private static final Singleton instance = new Singleton();

    // private static Singleton instance;

    private Singleton(){}

    public static Singleton getInstance(){
        // if(null == instance)
        //     instance = new Singleton();
        return instance;
    }

    public static void main(String[] args){
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        Singleton instance3 = Singleton.getInstance();
        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance3);

    }

}
