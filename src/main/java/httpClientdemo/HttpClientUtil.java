package httpClientdemo;

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

    public static void execute(HttpUriRequest request) {
        CloseableHttpResponse response = null;
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

            String res = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null)   response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
