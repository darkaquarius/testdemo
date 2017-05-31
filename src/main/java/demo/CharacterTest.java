package demo;

import org.junit.Test;

/**
 * Created by huishen on 17/1/19.
 */
public class CharacterTest {

    @Test
    public void test(){
        String str1 = "hello";
        byte[] bytes1 = str1.getBytes();
        if(bytes1.length >= 3){
            System.out.println("bigger than 3 characters");
        }

        String str2 = "你好";
        byte[] bytes2 = str2.getBytes();
        if(bytes2.length >= 3){
            System.out.println("bigger than 3 characters");
        }
    }
}
