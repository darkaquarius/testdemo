package demo;

/**
 * @author huishen
 * @date 18/3/9 下午8:41
 */
public class ReflectionDemo03 {

    public static void main(String[] args) {
        Class<?>[] array = new Class<?>[] {String.class, String[].class, Integer.class, Integer[].class};
        System.out.println();

        Class<?> clazz = String[].class;
        // class [Ljava.lang.String]
        System.out.println();

    }

}
