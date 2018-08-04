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

}
