package httpClientdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by huishen on 17/7/19.
 *
 */
public class HttpAsyncClientDemo {

    @Test
    public void test1() throws IOException {
        // TODO: 17/7/19 计算时间的？
        // 这是同步方法
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < 50; i++) {
            long start = System.currentTimeMillis();
            HttpGet httpGet = new HttpGet("http://www.baidu.com");
            // httpGet.setHeader("Connection", "close");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            response.close();
            System.out.println(System.currentTimeMillis() - start);
        }
        httpClient.close();
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException, IOException {
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();

        final HttpGet httpGet = new HttpGet("http://www.baidu.com");
        httpGet.setHeader("Connection", "close");
        List<Future<HttpResponse>> respList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            respList.add(httpAsyncClient.execute(httpGet, null));
        }

        for (Future<HttpResponse> responseFuture : respList) {
            HttpResponse response = responseFuture.get();
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test3() throws InterruptedException, ExecutionException, IOException {
        // CountDownLatch
        final CountDownLatch latch3 = new CountDownLatch(1);

        // 1.创建HttpAsyncClient
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();

        // 2.get或者post请求
        final HttpGet request3 = new HttpGet("http://www.baidu.com");

        // 3.producer and consumer
        HttpAsyncRequestProducer producer3 = HttpAsyncMethods.create(request3);
        AsyncCharConsumer<HttpResponse> consumer3 = new AsyncCharConsumer<HttpResponse>() {

            HttpResponse response;

            @Override
            protected void onResponseReceived(final HttpResponse response) {
                this.response = response;
            }

            @Override
            protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
                // Do something useful
            }

            @Override
            protected void releaseResources() {
            }

            @Override
            protected HttpResponse buildResult(final HttpContext context) {
                return this.response;
            }

        };

        // 4.execute()
        httpclient.execute(producer3, consumer3, new FutureCallback<HttpResponse>() {

            public void completed(final HttpResponse response3) {
                latch3.countDown();
                System.out.println(request3.getRequestLine() + "->" + response3.getStatusLine());
            }

            public void failed(final Exception ex) {
                latch3.countDown();
                System.out.println(request3.getRequestLine() + "->" + ex);
            }

            public void cancelled() {
                latch3.countDown();
                System.out.println(request3.getRequestLine() + " cancelled");
            }

        });

        latch3.await();
    }

    @Test
    public void whenUseMultipleHttpAsyncClient_thenCorrect() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(50);

        // HttpAsyncClient
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm =
            new PoolingNHttpClientConnectionManager(ioReactor);
        CloseableHttpAsyncClient client =
            HttpAsyncClients.custom().setConnectionManager(cm).build();
        client.start();

        // get request and execute
        for (int i = 0; i < 50; i++) {
            HttpGet request = new HttpGet("http://www.baidu.com");
            new GetThread(client, request, countDownLatch).start();
        }

        countDownLatch.await();
    }

    public static class GetThread extends Thread {
        private final CloseableHttpAsyncClient httpClient;
        private final HttpGet httpget;
        private final CountDownLatch countDownLatch;

        public GetThread(CloseableHttpAsyncClient httpClient, HttpGet httpget, CountDownLatch countDownLatch) {
            this.httpClient = httpClient;
            this.httpget = httpget;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            long s= System.currentTimeMillis();
            try {
                Future<HttpResponse> responseFuture = null;
                HttpResponse response = null;
                for (int i = 0; i < 1; i++) {
                    responseFuture = httpClient.execute(httpget, null);
                }
                try {
                    response = responseFuture.get();
                    HttpEntity entity = response.getEntity();
                    if(entity!=null){
                        // System.out.println(Thread.currentThread().getName() + "test");
                    }
                } finally {
                    countDownLatch.countDown();
                    // response.close();
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName());
                e.printStackTrace();
            } finally {
                System.out.println(System.currentTimeMillis() - s);
            }
        }
    }


}
