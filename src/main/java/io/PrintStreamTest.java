package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class PrintStreamTest {

    private static String str  = "陈华应";

    public static void main(String[] args) throws IOException{

        @SuppressWarnings("unused")
        PrintStream ps1 = new PrintStream("D:\\ps.txt");
        @SuppressWarnings("unused")
        PrintStream ps2 = new PrintStream(new File("D:\\ps.txt"));
        /**
         * 上边两个方法等价于下面的两个构造方法、
         * 带有指定字符集的PrintStream也是一样。
         */
        @SuppressWarnings("unused")
        PrintStream ps1_1 = new PrintStream(new FileOutputStream("D:\\ps.txt"));
        @SuppressWarnings("unused")
        PrintStream ps2_1 = new PrintStream(new FileOutputStream(new File("D:\\ps.txt")));

        /**
         * PrintStream 能够指定编码的原理：PrintStream的构造方法中、内部又用OutputStreamWriter将PrintStream包装了一层、
         * OutputstreamWriter构造时可以指定编码、PrintStream方法是调用
         */

        PrintStream ps3 = new PrintStream("D:\\ps3.txt" , "GBK");
        PrintStream ps4 = new PrintStream(new File("D:\\ps4.txt"),"GBK");

        /**
         * PrintStream的write方法本质是调用传入的OutputStream类的write方法、只是在指定PrintStream自动刷新时
         * 根据不同的字符（换行符 "\n"）自动刷新out中的流。
         */
        byte[] buf = {0x61, 0x62, 0x63, 0x64};
        ps3.write(buf);
        ps3.write(buf, 0, 0);

        /**
         * PrintStream的print()分两类、一个是调用自身的write(char[] c)、另一个是调用自身的write(String s)
         * 这两个方法都是调用OutputStreamWriter的write(char[] c,int off, int len)和write(String s, int off, int len)
         * 参数不对是因为：PrintStream的两个write方法不是直接调用OutputStreamWriter的上面两个方法、而是通过Writer这个抽象类来调用。
         *
         * 规律：
         * 1）只有print方法的参数时char[] 时调用write(char[] c)其他的都是调用write(String s)、只是把参数转换成String
         * 2）println方法是 调用上面的print方法之后加上PrintStream的 newLine()两个方法的组合、newLine()是向out中打印一个换行符。
         */
        ps3.println();
        ps3.println("============");
        StudentDTO student = new StudentDTO(1,"andyChen");
        /**
         * 只能做简单的记录、因为他是将Object对象obj.toString()这个字符串的值写入out中的。取出来的时候已经不能转换成Object了
         * 若要保存java基础类型到out中、可以使用DataOutputStream写入、用DataInputStream读取
         * 也可以使用ObjectInputStream读取ObjectOutputStream写入的被序列换的Object对象。后面会有叙述。
         */

        ps3.print(student);
        //向PrintStream中追加 传入的参数、参数可以是char 、CharSequence的全部和一部分、本质也是调用OutputStreamWriter的write方法。
        ps3.append("efg");

        /**
         * 下面的两个方法是用不同的编码从上面PrintStream写入的文件中读取数据、只拿一个中文字符串来测试
         */
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("D:\\ps4.txt")));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\ps4.txt")),"GBK"));
        ps4.println(str);


        byte[] b = new byte[bis.available()];
        int n = bis.read(b);
        System.out.println("result size: "+ n + "  str: " + new String(b));

        String result = br.readLine();
        System.out.println("result size: "+ n + "  str: " + result);
        /**
         * 结果分析：
         * 1）我们写入时指定的编码是GBK、而第一个读取时使用的默认的编码UTF-8、结果乱码
         * 2）指定用GBK来读取、正常显示
         * 从这里也可以看出、PrintStream提供 的指定编码的功能就是通过OutputStreamWriter来对PrintStream在ps内部进行了装饰、
         * 即打印时提供指定编码功能。
         *
         */
    }
}

