package demo;

import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author huishen
 * @date 2018/11/30 下午7:27
 */
public class ProcessDemo {

    /**
     * ProcessBuilder的start方法创建一个进程
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("ifconfig");
        Process process = pb.start();
        Scanner scanner = new Scanner(process.getInputStream());

        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Runtime的exec方法创建一个进程
     *
     * 实际上通过Runtime的exec创建进程的话，最终还是通过ProcessBuilder的start方法来创建的。
     */
    @Test
    public void test2() throws IOException {
        String cmd = "ifconfig";
        Process process = Runtime.getRuntime().exec(cmd);
        Scanner scanner = new Scanner(process.getInputStream());

        while(scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }

}
