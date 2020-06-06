package demo;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author huishen
 * @date 2018/7/25 上午10:24
 */
public class ByteDemo {

    @Test
    public void test1() throws UnsupportedEncodingException {
        String str = "中国彩七七彩票销售网络科技有限公司--福利彩票,体彩福彩3d,时时彩、六合彩、北京赛车、彩票网投注、香港六合彩11选5、北京快三、北京赛车,时时彩彩票,时时彩,北京赛车,快三,幸运28,福利彩票,福彩3d,体育彩票,256彩票,369彩票,699彩票,11选5,pk10,pc蛋蛋,500彩票,重庆时时彩,广东快三,北京pk拾,彩77彩票,9万彩票,106彩票,369彩票,彩6彩票,五百万彩票,500彩票,新彩,六合彩,六合彩开奖直播";
        byte[] bytes = str.getBytes("utf-8");
        System.out.println("字节数：" + bytes.length);
    }

    @Test
    public void test2() {
        byte b = 71;
        char c = (char) (b & 0xFF);
//        char c = b;
        System.out.println(c);

    }

    /**
     * byte[]    ->     int
     * 字节数组转int
     */
    public static int byte2Int(byte[] bytes) {
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }

    /**
     * int  -> byte[]
     * int转字节数组
     */
    public static byte[] intToByte(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);
        return bytes;
    }

    @Test
    public void test3() {
        byte[] str1 = "hello".getBytes();
        byte[] str2 = "world1".getBytes();


        byte[] str1Length = intToByte(str1.length); // str1Length == 4
        byte[] str2Length = intToByte(str2.length); // str2Length == 4

        byte[] ret = new byte[4 + 4 + str1.length + str2.length];

        System.arraycopy(str1Length, 0, ret, 0, 4);
        System.arraycopy(str2Length, 0, ret, 4, 4);
        System.arraycopy(str1, 0, ret, 8, str1.length);
        System.arraycopy(str2, 0, ret, 8 + str1.length, str2.length);


        System.out.println("序列化结束");
        System.out.println("反序列化开始");

        byte[] ret1LengthByte = new byte[4];
        byte[] ret2LengthByte = new byte[4];

        System.arraycopy(ret, 0, ret1LengthByte, 0, 4);
        int ret1Length = byte2Int(ret1LengthByte);

        System.arraycopy(ret, 4, ret2LengthByte, 0, 4);
        int ret2Length = byte2Int(ret2LengthByte);

        byte[] ret1 = new byte[ret1Length];
        byte[] ret2 = new byte[ret2Length];


        System.arraycopy(ret, 8, ret1, 0, ret1Length);
        System.arraycopy(ret, 8 + ret1Length, ret2, 0, ret2Length);

        System.out.println("ret1:" + new String(ret1));
        System.out.println("ret2:" + new String(ret2));
    }


    // 4个字节能表示足够大的数
    @Test
    public void test4() {
        int length = 1_000_000;
        byte[] bytes = intToByte(length);
        int ret = byte2Int(bytes);
        System.out.println(ret);
    }

    // 说明intToByte()和byte2Int()在十亿以内是正确的
    // Integer.MAX_VALUE == 2_147_483_647
    @Test
    public void test5() {
        for (int i = 1; i <= 2_000_000_000; i = i + 56) {
            byte[] bytes = intToByte(i);
            int ret = byte2Int(bytes);
//            System.out.println(String.format("i:%s, ret:%s", i, ret));
            if (i != ret) {
                System.out.println(String.format("i != ret, i:%s, ret: %s", i, ret));
                break;
            }
        }
        System.out.println("end");
    }

    @Test
    public void test6() {
        for (int i = 1; i <= 1_000_000; i = i + 26) {
            byte[] bytes = intToByte(i);
//            System.out.println(String.format("i:%s,bytes length:%s", i, bytes.length));
            if (bytes.length != 4) {
                System.out.println(String.format("i:%s, bytes length != 4, bytes length is:%s", i, bytes.length));
            }
        }
        System.out.println("end");
    }

    @Test
    public void test7() {
        System.out.println(Integer.MAX_VALUE);
    }

    @Test
    public void test8() {
        byte[] bytes = new byte[]{12,13,15};
        String str = new String(bytes);
        byte[] bytes1 = str.getBytes();

        System.out.println();
    }


}
