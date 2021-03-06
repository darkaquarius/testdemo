package httpClientdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by huishen on 17/5/13.
 *
 */
public class HttpClientDemo {

    /**
     * 用多线程来执行HttpClient
     */
    @Test
    public void test1() throws InterruptedException,ExecutionException, IOException {
        long s = System.currentTimeMillis();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);//设置最大连接数200
        cm.setDefaultMaxPerRoute(100);//设置每个路由默认连接数
        HttpHost host = new HttpHost("webservice.webxml.com.cn");//针对的主机
        cm.setMaxPerRoute(new HttpRoute(host), 100);//每个路由器对每个服务器允许最大5个并发访问

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(2000)
            .setConnectTimeout(2000)
            .setSocketTimeout(3000)
            .setRedirectsEnabled(false) //不允许重定向
            // .setCookieSpec(CookieSpecs.DEFAULT)  // cookie
            .build();

        SocketConfig socketConfig = SocketConfig.custom()
            .setTcpNoDelay(true)
            .setSoReuseAddress(true)
            .setSoKeepAlive(true)
            .setSoTimeout(3000)
            .build();


        CloseableHttpClient httpClient = HttpClients
            .custom()
            // .setConnectionManager(cm)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultSocketConfig(socketConfig)
            // .setRetryHandler(new MyRetryHandler())
            // .setRetryHandler(new StandardHttpRequestRetryHandler())
            // .setRedirectStrategy()
            .build();

        String[] urisToGet = {
            "http://test.api.fangsdai.com:81/api/v1/activity/trainingDataSave",
            "http://www.baidu.com",
            "http://118.178.58.124:8088/v1/ads/source_app/com.whitebeard.nomoneyme/",
            "http://118.178.58.124:8088/v1/ads/detail/app_store_id/1037599157",
            "http://118.178.58.124:8088/v1/ads/banner"
        };

        GetThread[] threads = new GetThread[urisToGet.length];

        for (int i = 0; i < threads.length; i++) {
            // CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).setDefaultSocketConfig(socketConfig).build();
            HttpGet httpget = new HttpGet(urisToGet[i]);
            threads[i] = new GetThread(httpClient, httpget);
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }

        System.out.println("main thread end, spend:" + (System.currentTimeMillis() - s));
    }

    public static class GetThread extends Thread {
        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
            this.httpClient = httpClient;
            this.context = HttpClientContext.create();
            this.httpget = httpget;
        }

        @Override
        public void run() {
            try {
                CloseableHttpResponse response = null;
                for (int i = 0; i < 1; i++) {
                    response = httpClient.execute(httpget, context);
                    Thread.sleep(10);
                }
                try {
                    HttpEntity entity = response.getEntity();
                    if(entity!=null){
                        System.out.println(Thread.currentThread().getName() + "test");
                    }
                } finally {
                    response.close();
                }
            } catch (ClientProtocolException ex) {
                System.out.println(Thread.currentThread().getName());
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println(Thread.currentThread().getName());
                ex.printStackTrace();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
    }

}
