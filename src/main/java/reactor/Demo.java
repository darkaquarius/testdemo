package reactor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by huishen on 17/7/12.
 *
 */
public class Demo {

    public static void main(String[] args) {

        Consumer<String> consumer =  System.out::println;

        Function<Integer, String> transformation = integer -> "" + integer;

        Supplier<Integer> supplier = () -> 123;

        BiConsumer<Consumer<String>, String> biConsumer = (callback, value) -> {
            for (int i = 0; i < 10; i++) {
                // 对要运行的最后逻辑运行做惰性求值
                callback.accept(value);
            }
        };

        // 注意生产者到双向消费者执行过程
        biConsumer.accept(consumer, transformation.apply(supplier.get()));

    }

}
