package demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import static sun.security.pkcs11.wrapper.Functions.toHexString;

/**
 * Created by huishen on 16/12/11.
 * 加解密相关方法
 */

public class DigestUtilsDemo {

    /**
     * 生成MD5
     * DigestUtils.md5Hex
     */
    @Test
    public void testMd5Hex(){
        String id1 = "58";
        String id2 = 58 + "";
        String pwd1 = DigestUtils.md5Hex(id1);
        String pwd2 = DigestUtils.md5Hex(id2);
        System.out.println(id1);
        System.out.println(id2);
        System.out.println(pwd1);
        System.out.println(pwd2);
    }

    /**
     * 生成sha1
     * MessageDigest.getInstance("SHA-1")
     * sha1.update(buffer, 0, numRead);
     */
    @Test
    public void testSha1Digest1() {
        String sha1Digest = sha1Digest("/Users/huishen/run.sh");
        System.out.println(sha1Digest);
    }

    /**
     * 生成sha1
     * DigestUtils.sha1Hex(bytes);
     */
    @Test
    public void testSha1Digest2() {
        try (InputStream input = new FileInputStream("/Users/huishen/run.sh")) {
            byte[] bytes = new byte[input.available()];
            int read = input.read(bytes);
            String sha1Hex = DigestUtils.sha1Hex(bytes);
            System.out.println(sha1Hex);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // @Test
    // public void testSha1Digest3() {
    //     try (Reader reader = new FileReader("/Users/huishen/run.sh")) {
    //         char[] chars = new char[reader.]
    //         reader.read()
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public static String sha1Digest(String filename) {
        InputStream fis = null;
        // byte[] buffer = new byte[Constants.BUFFER_SIZE];
        byte[] buffer = new byte[128];
        int numRead = 0;
        MessageDigest sha1;
        try {
            fis = new FileInputStream(filename);
            sha1 = MessageDigest.getInstance("SHA-1");
            while ((numRead = fis.read(buffer)) > 0) {
                sha1.update(buffer, 0, numRead);
            }
            return toHexString(sha1.digest());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
