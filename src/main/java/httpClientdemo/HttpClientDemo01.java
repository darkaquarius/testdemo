package httpClientdemo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by huishen on 17/6/22.
 * 最简单的HttpClient请求
 */
public class HttpClientDemo01 {

    // 可以使http请求，也可以是https请求
    public static void main(String[] args) throws IOException {

        String url = "https://www.baidu.com";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = httpClient.execute(httpGet);

        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(response.getEntity(), Charset.defaultCharset()));

    }

}
