 package demo;

 import org.apache.commons.codec.digest.DigestUtils;
 import org.junit.Test;

 import javax.crypto.Mac;
 import javax.crypto.SecretKey;
 import javax.crypto.spec.SecretKeySpec;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.Reader;
 import java.io.UnsupportedEncodingException;
 import java.security.InvalidKeyException;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.util.Arrays;

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
         String str = "123456";
         String pwd = DigestUtils.md5Hex(str);
         System.out.println(str);
         System.out.println(pwd);
     }

     @Test
     public void testMd5() {
         String str = "123456";
         byte[] pwd = DigestUtils.md5(str);
         System.out.println(str);
         System.out.println(pwd);
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

     private static String sha1Digest(String filename) {
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

     /**
      * 生成sha1
      * 字节流
      * DigestUtils.sha1Hex(bytes);
      */
     @Test
     public void testSha1Digest2() {
         try (InputStream input = new FileInputStream("/Users/huishen/run.sh")) {
             // 注意：input.available()返回的是int类型，最大支持2G的文件大小
             byte[] bytes = new byte[input.available()];
             int read = input.read(bytes);
             String sha1Hex = DigestUtils.sha1Hex(bytes);
             System.out.println(sha1Hex);
         } catch (IOException e) {
             e.printStackTrace();
         }

     }

     /**
      * 生成sha1
      * 字符流
      * DigestUtils.sha1Hex(String)
      */
     @Test
     public void testSha1Digest3() {
         File file = new File("/Users/huishen/run.sh");
         try (Reader reader = new FileReader(file)) {
             char[] chars = new char[(int)file.length()];
             int read = reader.read(chars);
             String sha1Hex = DigestUtils.sha1Hex(String.valueOf(chars));
             System.out.println(sha1Hex);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     /**
      * 生成HMAC-SHA1
      * 下同
      */
     @Test
     public void testHmacSha1()
         throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {

         String src = "1aasad2576nygd3";
         String APP_SECRET = "app_secret";

         Mac mac = Mac.getInstance("HmacSHA1");
         SecretKeySpec secret =
             new SecretKeySpec(APP_SECRET.getBytes("UTF-8"), mac.getAlgorithm());
         mac.init(secret);
         byte[] bytes = mac.doFinal(src.getBytes());
     }

     /**
      * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
      * @param encryptText 被签名的字符串
      * @param encryptKey  密钥
      * @return
      * @throws Exception
      */
     public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
         final String MAC_NAME = "HmacSHA1";
         final String ENCODING = "UTF-8";

         byte[] data=encryptKey.getBytes(ENCODING);
         //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
         SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
         //生成一个指定 Mac 算法 的 Mac 对象
         Mac mac = Mac.getInstance(MAC_NAME);
         //用给定密钥初始化 Mac 对象
         mac.init(secretKey);

         byte[] text = encryptText.getBytes(ENCODING);
         //完成 Mac 操作
         return mac.doFinal(text);
     }


 }
