package httpClientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huishen on 17/6/22.
 * 测试所有的demo
 */
@Slf4j
public class HttpClientMainTest {

    public static void main(String[] args) {

        execGet("http://www.baidu.com");
        // execPost("http://123.206.202.131:8088/v1/users/login");
        // execGetSSL( "https://api.searchads.apple.com/api/v1/acls");

    }

    private static void execGet(String url) {
        //httpGet
        HttpGet httpGet = new HttpGet(url);
        // httpGet.setHeader("Accept", "text/html, */*; q=0.01");
        // httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        // httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        // httpGet.setHeader("Referer", url);
        // httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:46.0) Gecko/20100101 Firefox/46.0");

        HttpClientUtil.execute(httpGet);
    }

    private static void execPost2(String url) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpClientUtil.execute(httpPost);
    }

    private static void execPost(String url) {
        //httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(getHttpEntity());

        HttpClientUtil.execute(httpPost);
    }

    private static void execGetSSL(String url) {
        //httpGet
        HttpGet httpGet = new HttpGet(url);

        HttpClientUtil.execute(httpGet);
    }

    // @SuppressWarnings("Duplicates")
    // private CloseableHttpResponse execPostSSL(CloseableHttpClient httpClient, String url) {
    //
    //     HttpPost httpPost = new HttpPost();
    //
    // }

    private static HttpEntity getHttpEntity() {
        //一个对象
        Map<String, String> map = new HashMap<>();
        map.put("mobile_phone", "13888888888");
        map.put("pwd", "123456");

        //序列化
        String s = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            s = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new StringEntity(s, "utf-8");
    }

    @Test
    public void test() {
        String url = "https://twitter.com/";
        HttpGet httpGet = new HttpGet(url);
        HttpClientUtil.execute(httpGet);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testIOS12() {
        // final RateLimiter rateLimiter = RateLimiter.create(1.5);
        String uri = "https://api-edge.apps.apple.com/v1/catalog/cn/search?" +
            "platform=iphone" +
            // "&extend=editorialBadgeInfo%2CmessagesScreenshots%2CminimumOSVersion%2CrequiredCapabilities%2CscreenshotsByType%2CsupportsFunCamera%2CvideoPreviewsByType"+
            "&include=apps%2Ctop-apps" +
            "&bubble%5Bsearch%5D=apps%2Cdevelopers%2Cgroupings%2Ceditorial-items%2Capp-bundles%2Cin-apps" +
            "&l=zh-Hans-CN" +
            "&term=qq";
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", "Bearer eyJraWQiOiJGNDdEWk4xOEYwIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiJBUzI4UjdHMTdNIiwiaWF0IjoxNTU5NjMwODQ5LCJleHAiOjE1NjIyMjI4NDl9.-aKQy-fbhDEAashHn9C-7oGeAm1Dks0xa4mGd54DiYEF51M2hRw-car9KiAsPWNlPgOXK7IyssF55-XVnie5Kw");
        httpGet.setHeader("User-Agent", "AppStore/3.0 iOS/12.1.2 model/iPhone9,1 hwp/t8010 build/16C101 (6; dt:137) AMS/1");
        httpGet.setHeader("X-Apple-Store-Front", "143465-19,29 t:apps3");

        long s = System.currentTimeMillis();
        int statusCode;
        // rateLimiter.acquire();
        statusCode = HttpClientUtil.execute(httpGet);
        System.out.println("thread:" + Thread.currentThread().getName());
        System.out.println("statusCode: " + statusCode);
        System.out.println("spends: " + (System.currentTimeMillis() - s));

    }


    @SuppressWarnings("Duplicates")
    @Test
    public void testIOS12_1() {
        final RateLimiter rateLimiter = RateLimiter.create(1.5);
        String uri = "https://api-edge.apps.apple.com/v1/catalog/cn/search?" +
            "platform=iphone" +
            "&extend=editorialBadgeInfo,messagesScreenshots,minimumOSVersion,requiredCapabilities,screenshotsByType,supportsFunCamera,videoPreviewsByType" +
            "&include=apps,top-apps" +
            "&bubble[search]=apps,developers,groupings,editorial-items,app-bundles,in-apps" +
            "&l=zh-Hans-CN" +
            "&term=qq";
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", "Bearer eyJraWQiOiJGNDdEWk4xOEYwIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiJBUzI4UjdHMTdNIiwiaWF0IjoxNTQ2OTMyMjM2LCJleHAiOjE1NDk1MjQyMzZ9.J_mpKOQ45NLLJ7SZDVlsic1f8DrqiMMhQ6y9ShXStcljgX2bYpH77TJFscglyUAFHnng7neZjVK7eeHFSLFgOg");
        httpGet.setHeader("User-Agent", "AppStore/3.0 iOS/12.1.1 model/iPhone9,1 hwp/t8010 build/16C50 (6; dt:137) AMS/1");
        httpGet.setHeader("X-Apple-Store-Front", "143465-19,29");

        long s = System.currentTimeMillis();

        final int size = 25;
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0 ; i < size; i++) {
            executorService.submit(() -> {
                int statusCode;
                int count = 0;
                do {
                    rateLimiter.acquire();
                    statusCode = HttpClientUtil.execute(httpGet);
                    count++;
                    System.out.println("thread:" + Thread.currentThread().getName() + ", status: " + statusCode);
                } while (statusCode != 429);
                System.out.println("thread:" + Thread.currentThread().getName());
                System.out.println("statusCode: " + statusCode);
                System.out.println("spends: " + (System.currentTimeMillis() - s));
                System.out.println("count: " + count);
            });
        }

        while(Thread.activeCount() > 1) {
            Thread.yield();
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testIOS12_2() throws IOException, InterruptedException {
        FileOutputStream fos = new FileOutputStream("/Users/huishen/test.txt", true);

        String uri = "https://api-edge.apps.apple.com/v1/catalog/cn/search?" +
            "platform=iphone" +
            // "&extend=editorialBadgeInfo%2CmessagesScreenshots%2CminimumOSVersion%2CrequiredCapabilities%2CscreenshotsByType%2CsupportsFunCamera%2CvideoPreviewsByType"+
            "&include=apps%2Ctop-apps" +
            "&bubble%5Bsearch%5D=apps%2Cdevelopers%2Cgroupings%2Ceditorial-items%2Capp-bundles%2Cin-apps" +
            "&l=zh-Hans-CN" +
            "&term=投资";
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", "Bearer eyJraWQiOiJGNDdEWk4xOEYwIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiJBUzI4UjdHMTdNIiwiaWF0IjoxNTU5NjMwODQ5LCJleHAiOjE1NjIyMjI4NDl9.-aKQy-fbhDEAashHn9C-7oGeAm1Dks0xa4mGd54DiYEF51M2hRw-car9KiAsPWNlPgOXK7IyssF55-XVnie5Kw");
        httpGet.setHeader("User-Agent", "AppStore/3.0 iOS/12.1.1 model/iPhone9,1 hwp/t8010 build/16C50 (6; dt:137) AMS/1");
        httpGet.setHeader("X-Apple-Store-Front", "143465-19,29");

        while (true) {
            System.out.println("68 times start...");
            for (int i = 0; i < 68; i++) {
                int statusCode = HttpClientUtil.execute(httpGet);
                System.out.println(statusCode);
                fos.write(String.valueOf(statusCode).concat("\n").getBytes());
            }
            fos.write("\n\n".getBytes());
            System.out.println("68 times end, sleep...");
            Thread.sleep(1000 * 72);
        }

    }

}
