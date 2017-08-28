package httpClientdemo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huishen on 17/8/28.
 * http protocol interceptors
 */

public class HttpProtocolInterceptorDemo {

    @Test
    public void test01() {

        // why does NOT work??
        // System.setProperty("http.proxySet", "true");
        // System.setProperty("http.proxyHost", "127.0.0.1");
        // System.setProperty("http.proxyPort", "8888");

        HttpHost proxy = new HttpHost("localhost", 8888);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        CloseableHttpClient httpClient = HttpClients.custom()
            .setRoutePlanner(routePlanner)
            .addInterceptorLast(
            (HttpRequest request, HttpContext context) -> {

                AtomicInteger count = (AtomicInteger) context.getAttribute("count");
                request.addHeader("Count", Integer.toString(count.getAndIncrement()));

            }).build();

        AtomicInteger count = new AtomicInteger(1);
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAttribute("count", count);

        HttpGet httpGet = new HttpGet("http://localhost:8088/tmp111/v1/users/httpclient_test");
        // HttpGet httpGet = new HttpGet("http://loan.v1.guangjuhuizhan.cn/v1/ads/url/com.loan.muyi/");

        for (int i = 0; i < 5; i++) {

            try (CloseableHttpResponse response = httpClient.execute(httpGet, localContext)) {
                Header[] allHeaders = response.getAllHeaders();
                HttpEntity entity = response.getEntity();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
