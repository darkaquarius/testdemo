package httpClientdemo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by huishen on 17/6/24.
 * HttpClientUtil
 */

public class HttpClientUtil {

    private static CloseableHttpClient httpClient;

    private static HttpClientContext context;

    static {
        HttpClientDemo02 httpClientDemo = new HttpClientDemo02();
        // HttpClientDemo03 httpClientDemo = new HttpClientDemo03();

        try {
            httpClient = httpClientDemo.getHttpClient();

            // HttpClientContext, only for HttpClientDemo03 instance
            // context = httpClientDemo.getHttpClientContext();
        } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

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
                EntityUtils.consume(entity);
                // 使用连接池的话，这里的httpClient不用close
                // httpClient.close();
                if (response != null)   response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
