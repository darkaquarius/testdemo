package java8inactiondemo.chap03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

/**
 * Created by huishen on 17/7/10.
 *
 */
public class ExecuteAround {

    public static void main(String[] args) throws IOException {
        processFile(br -> br.readLine() + br.readLine());

        // 如果采用通用的Function接口，不能抛出异常，只能try...catch...
        // 或者和上面一样，自定义一个函数接口
        processFile2(br -> {
            try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    // pre java8, 只能读取一行
    public static String preProcessFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/data.txt"))) {
            return br.readLine();
        }
    }

    // java8, 行为参数化, 可以任意改变br的行为
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/data.txt"))) {
            return p.process(br);
        }
    }

    public static String processFile2(Function<BufferedReader, String> function) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/data.txt"))) {
            return function.apply(br);
        }
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader br) throws IOException;
    }

}
