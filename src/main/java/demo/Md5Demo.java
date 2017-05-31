package demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by huishen on 16/12/11.
 */
public class Md5Demo {

    @Test
    public void test(){
        String id1 = "58";
        String id2 = 58 + "";
        String pwd1 = DigestUtils.md5Hex(id1);
        String pwd2 = DigestUtils.md5Hex(id2);
        System.out.println(id1);
        System.out.println(id2);
        System.out.println(pwd1);
        System.out.println(pwd2);

    }
}
