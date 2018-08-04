package httpClientdemo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by huishen on 17/6/24.
 * HttpClientUtil
 * 用不同得到 HttpClient
 */

public class HttpClientUtil {

    private static CloseableHttpClient httpClient;

    private static HttpClientContext context;

    static {
        // HttpClientDemo httpClientDemo = new HttpClientDemo03();
        // HttpClientDemo02 httpClientDemo = new HttpClientDemo02();
        HttpClientDemo03 httpClientDemo = new HttpClientDemo03();

        // try {
            httpClient = httpClientDemo.getHttpClient();

            // HttpClientContext, only for HttpClientDemo03 instance
            context = httpClientDemo.getHttpClientContext();
        // } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
        //     e.printStackTrace();
        // }

    }

    // 这里把get和post请求合并到一起写了
    // get和post请求的区别是传入的参数
    public static void execute(HttpUriRequest request) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            if (context != null) {
                response = httpClient.execute(request, context);
            } else {
                response = httpClient.execute(request);
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                System.out.println("response-error");
            }

            entity = response.getEntity();
            String res = EntityUtils.toString(entity, Charset.defaultCharset());
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 使用连接池的话，这里的httpClient不用close
                // httpClient.close();
                if (response != null) {
                    response.close();
                    // consume()其实就是instream.close()，作用就是将用完的连接释放，下次请求可以复用。
                    // 如果不使用in.close();而仅仅使用response.close();结果就是连接会被关闭，并且不能被复用，这样就失去了采用连接池的意义。
                    // EntityUtils.consume(entity);

                    // 连接池释放连接的时候，并不会直接对TCP连接的状态有任何改变，
                    // 只是维护了两个Set，leased和avaliabled，leased代表被占用的连接集合，avaliabled代表可用的连接的集合，
                    // 释放连接的时候仅仅是将连接从leased中remove掉了，并把连接放到avaliabled集合中
                    // without throwing any IOException.
                    EntityUtils.consumeQuietly(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
