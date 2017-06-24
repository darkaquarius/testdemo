package httpClientdemo;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by huishen on 17/5/17.
 * x509Certificates来请求https
 */
public class HttpClientDemo02 {

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
        SSLConnectionSocketFactory sslsf =
            new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

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
            // .setProxy(new HttpHost("45.32.21.237", 8888, "HTTP"))
            .build();

        socketConfig = SocketConfig.custom()
            .setTcpNoDelay(true)
            .setSoReuseAddress(true)
            .setSoKeepAlive(true)
            .setSoTimeout(1000 * 60)
            .build();
    }

    public CloseableHttpClient getHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return HttpClients
            .custom()
            .setConnectionManager(cm)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultSocketConfig(socketConfig)
            // .setKeepAliveStrategy()
            // .setConnectionManagerShared(true)
            // .setProxy(new HttpHost("45.32.21.237", 8888, "HTTP"))      // 代理
            .build();
    }

}
