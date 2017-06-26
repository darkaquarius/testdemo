package httpClientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huishen on 17/6/22.
 * 测试所有的demo
 */
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

}
