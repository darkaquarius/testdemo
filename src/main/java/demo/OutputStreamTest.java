package demo;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by huishen on 17/2/17.
 */
public class OutputStreamTest {

    @Test
    public void testFileOutputStream() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("/data/hui.txt"));
            fos.write("hello world".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testByteArrayOutputStream() {
        byte[] buffer = "hello world".getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        baos.write(buffer, 0, 11);
    }

    @Test
    public void test2() {
        ByteArrayOutputStream bOutput = new ByteArrayOutputStream(12);
        while (bOutput.size() != 10) {
            // 获取用户输入
            bOutput.write(100);
        }
        byte b[] = bOutput.toByteArray();
        System.out.println("Print the content");
        for (int x = 0; x < b.length; x++) {
            // 打印字符
            System.out.print((char) b[x] + "   ");
        }
        System.out.println("   ");
        int c;
        ByteArrayInputStream bInput = new ByteArrayInputStream(b);
        System.out.println("Converting characters to Upper case ");
        for (int y = 0; y < 1; y++) {
            while ((c = bInput.read()) != -1) {
                System.out.println(Character.toUpperCase((char) c));
            }
            bInput.reset();
        }
    }

    public static void main(String args[]){
        String str = "HELLOWORLD" ;     // 定义一个字符串，全部由大写字母组成
        ByteArrayInputStream bis = null ;   // 内存输入流
        ByteArrayOutputStream bos = null ;  // 内存输出流
        bis = new ByteArrayInputStream(str.getBytes()) ;    // 向内存中输入内容
        bos = new ByteArrayOutputStream() ; // 准备从内存ByteArrayInputStream中读取内容
        int temp = 0 ;
        while((temp=bis.read())!=-1){
            char c = (char) temp ;  // 读取的数字变为字符
            bos.write(Character.toLowerCase(c)) ;   // 将字符变为小写
        }
        // 所有的数据就全部都在ByteArrayOutputStream中
        String newStr = bos.toString() ;    // 取出内容
        String s = bis.toString();
        try{
            bis.close() ;
        }catch(IOException e){
            e.printStackTrace() ;
        }
        System.out.println(newStr) ;
    }

    @Test
    public void test3() {
        String str1 = "hello world";
        String str2 = "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bis = new ByteArrayInputStream(str1.getBytes());
        bos.write(str2.getBytes(), 0, 11);
        System.out.println(bos);
    }


}
