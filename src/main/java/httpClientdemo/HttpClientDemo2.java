package httpClientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/5/17.
 *
 */
public class HttpClientDemo2 {

    private static PoolingHttpClientConnectionManager cm;

    private static RequestConfig requestConfig;

    private static SocketConfig socketConfig;

    static {
        KeyStore keyStore = null;
        SSLContext sslContext = null;
        try {
            //sslContext
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            TrustStrategy trustStrategy = (x509Certificates, s) -> true;
            sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, trustStrategy).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }

        //sslsf
        Assert.notNull(sslContext);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        //sslsf
        // KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // keyStore.load(is, pwd);
        //
        // SSLContext context = SSLContexts.custom().
        //     loadKeyMaterial(keyStore, pwd).build();
        // SSLConnectionSocketFactory sslsf =
        //     new ProxySSLConnectionSocketFacotry(context);

        //registry
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslsf)
            .build();

        //PoolingHttpClientConnectionManager
        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(1000);
        cm.setDefaultMaxPerRoute(100);
        new IdleConnectionMonitorThread(cm).start(); // connection monitor

        requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(2000)
            .setConnectTimeout(2000)
            .setSocketTimeout(1000 * 60)
            .setRedirectsEnabled(false) //不允许重定向
            .build();

        socketConfig = SocketConfig.custom()
            .setTcpNoDelay(true)
            .setSoReuseAddress(true)
            .setSoKeepAlive(true)
            .setSoTimeout(1000 * 60)
            .build();
    }

    private HttpClient getHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return HttpClients
            .custom()
            .setConnectionManager(cm)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultSocketConfig(socketConfig)
            // .setKeepAliveStrategy()
            // .setConnectionManagerShared(true)
            .build();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        HttpClientDemo2 httpClientDemo = new HttpClientDemo2();
        // httpClientDemo.execGet();
        httpClientDemo.execPost();
        httpClientDemo.execGetSSL();
    }

    public void execGet() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //httpClient
        HttpClient httpClient = getHttpClient();

        //httpGet
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        //httpResponse
        HttpResponse response = httpClient.execute(httpGet);

        dealWithResonse(response);
    }

    private void execPost() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        //httpClient
        HttpClient httpClient = getHttpClient();

        //httpPost
        HttpPost httpPost = new HttpPost("http://123.206.202.131:8088/v1/users/login");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(getHttpEntity());

        //httpResponse
        HttpResponse response = httpClient.execute(httpPost);

        dealWithResonse(response);
    }

    // private void execPostSSL() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    //     HttpClient httpClient = getHttpClient();
    //
    //     HttpPost httpPost = new HttpPost()
    //
    // }

    private void execGetSSL() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //httpClient
        HttpClient httpClient = getHttpClient();

        //httpGet
        HttpGet httpGet = new HttpGet("https://www.taobao.com/");

        //httpResponse
        HttpResponse response = httpClient.execute(httpGet);

        dealWithResonse(response);
    }

    private HttpEntity getHttpEntity() throws JsonProcessingException {
        //一个对象
        Map<String, String> map = new HashMap<>();
        map.put("mobile_phone", "13888888888");
        map.put("pwd", "123456");

        //序列化
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(map);
        return new StringEntity(s, "utf-8");
    }

    private void dealWithResonse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            System.out.println("response-error");
        }

        String res = EntityUtils.toString(response.getEntity());
        System.out.println(res);
    }

    private static class IdleConnectionMonitorThread extends Thread {
        private final PoolingHttpClientConnectionManager cm;
        private volatile boolean shutdown;

        IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connMgr) {
            super("http-client-idle-connection-monitor-thread");
            this.cm = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        cm.closeExpiredConnections();
                        cm.closeIdleConnections(1, TimeUnit.MINUTES);
                        // logger.info(cm.getTotalStats().toString());
                    }
                }
            } catch (InterruptedException ex) {
                shutdown();
            }
        }

        void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

}
