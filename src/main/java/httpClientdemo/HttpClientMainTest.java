package httpClientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huishen on 17/6/22.
 * 测试所有的demo
 */
public class HttpClientMainTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        // HttpClientDemo02 httpClientDemo = new HttpClientDemo02();
        HttpClientDemo03 httpClientDemo = new HttpClientDemo03();

        // httpClient
        CloseableHttpClient httpClient = httpClientDemo.getHttpClient();

        HttpClientContext context = null;
        // HttpClientContext, only for HttpClientDemo03 instance
        context = httpClientDemo.getHttpClientContext();

        CloseableHttpResponse response = null;
        try {
            // response = execGet(httpClient, "http://www.taobao.com", context);
            // response = execPost(httpClient, "http://123.206.202.131:8088/v1/users/login", context);
            response = execGetSSL(httpClient, "https://api.searchads.apple.com/api/v1/acls", context);
            dealWithResonse(response);
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    private static CloseableHttpResponse execGet(CloseableHttpClient httpClient, String url, HttpClientContext context)
        throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        //httpGet
        HttpGet httpGet = new HttpGet(url);
        // httpGet.setHeader("Accept", "text/html, */*; q=0.01");
        // httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        // httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        // httpGet.setHeader("Referer", url);
        // httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:46.0) Gecko/20100101 Firefox/46.0");

        if (context != null) {
            return httpClient.execute(httpGet, context);
        } else {
            return httpClient.execute(httpGet);
        }

    }

    private static CloseableHttpResponse execPost(CloseableHttpClient httpClient, String url, HttpClientContext context)
        throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        //httpPost
        HttpPost httpPost = new HttpPost();
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(getHttpEntity());

        if (context != null) {
            return httpClient.execute(httpPost, context);
        } else {
            return httpClient.execute(httpPost);
        }
    }

    private static CloseableHttpResponse execGetSSL(CloseableHttpClient httpClient, String url, HttpClientContext context)
        throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        //httpGet
        HttpGet httpGet = new HttpGet(url);

        if (context != null) {
            return httpClient.execute(httpGet, context);
        } else {
            return httpClient.execute(httpGet);
        }
    }

    // private CloseableHttpResponse execPostSSL(CloseableHttpClient httpClient, String url) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    //
    //     HttpPost httpPost = new HttpPost();
    //
    // }

    private static HttpEntity getHttpEntity() throws JsonProcessingException {
        //一个对象
        Map<String, String> map = new HashMap<>();
        map.put("mobile_phone", "13888888888");
        map.put("pwd", "123456");

        //序列化
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(map);
        return new StringEntity(s, "utf-8");
    }

    private static void dealWithResonse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            System.out.println("response-error");
        }

        String res = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
        System.out.println(res);
    }

}
