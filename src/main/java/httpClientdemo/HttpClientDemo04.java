package httpClientdemo;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author huishen
 * @date 2019-01-07 20:06
 * <p>
 * 基本的HttpClient请求
 */
public class HttpClientDemo04 {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientDemo04.class);

    private static PoolingHttpClientConnectionManager cm;

    private static RequestConfig requestConfig;

    private static SocketConfig socketConfig;

    private static final IdleConnectionEvictor idleConnectionEvictor;

    private static final Monitor monitor;

    private static final int TIMEOUT = 10000;

    private static final int maxTotal = 300;

    static {
        requestConfig = RequestConfig.custom()
            // wait for connection from pool
            .setConnectionRequestTimeout(TIMEOUT)
            .setConnectTimeout(TIMEOUT)
            .setSocketTimeout(TIMEOUT)
            // disable redirect
            .setRedirectsEnabled(false)
            .setRelativeRedirectsAllowed(false)
            .setCircularRedirectsAllowed(false)
            .build();

        SocketConfig socketConfig = SocketConfig.custom()
            .setSoTimeout(TIMEOUT)
            .setTcpNoDelay(true)
            .setSoReuseAddress(true)
            .setSoKeepAlive(true)
            .build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setCharset(StandardCharsets.UTF_8)
            .build();

        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxTotal);
        cm.setDefaultSocketConfig(socketConfig);
        cm.setDefaultConnectionConfig(connectionConfig);

        idleConnectionEvictor = new IdleConnectionEvictor(cm, 10, TimeUnit.SECONDS);
        monitor = new Monitor(cm);
    }

    public CloseableHttpClient getHttpClient() {

        // setRoutePlanner
        // HttpHost proxy = new HttpHost("localhost", 8888);
        // DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        return HttpClients
            .custom()
            .setConnectionManager(cm)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultSocketConfig(socketConfig)
            .setProxy(new HttpHost("103.60.137.2", 22056, "HTTP"))      // http代理
            .build();
    }




    public static class Monitor extends Thread {
        private final PoolingHttpClientConnectionManager connectionManager;
        private volatile boolean shutdown = false;

        public Monitor(PoolingHttpClientConnectionManager cm) {
            this.connectionManager = cm;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    logger.info("{}", connectionManager.getTotalStats());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public void shutdown() {
            this.shutdown = true;
        }
    }

}
