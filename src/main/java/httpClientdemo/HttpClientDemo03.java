package httpClientdemo;

/**
 * Created by huishen on 17/6/21.
 * httpclient使用socks5代理来请求http和https
 */

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;

public class HttpClientDemo03 {

    // 依次是代理地址，代理端口号，用户密码
    private static String proxyHost = "127.0.0.1";
    private static int proxyPort = 1080;
    private static String proxyName = "user";
    private static String proxyPwd = "123456";

    private static PoolingHttpClientConnectionManager cm;

    private static RequestConfig requestConfig;

    private static SocketConfig socketConfig;

    static {
        // 用户名和密码验证
        // Authenticator.setDefault(new Authenticator() {
        //     protected PasswordAuthentication getPasswordAuthentication() {
        //         return new PasswordAuthentication(proxyName, proxyPwd.toCharArray());
        //     }
        // });

        // SSLContext
        SSLContext context = null;
        // 默认
        // context = SSLContexts.createSystemDefault();
        // 动态加载证书
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream is = new FileInputStream("/Users/huishen/certs/xiaochen_entire_admin.p12");
            char[] pwd = "123".toCharArray();
            keyStore.load(is, pwd);
            context = SSLContexts.custom().loadKeyMaterial(keyStore, pwd).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // try {
        //     String pwd = "123";
        //     // jks
        //     KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        //     FileInputStream is = new FileInputStream("/Users/huishen/certs/xiaochen_entire_admin.p12");
        //     keyStore.load(is, pwd.toCharArray());
        //     TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        //     tmf.init(keyStore);
        //     context = SSLContext.getInstance("TLS");
        //     context.init(null, tmf.getTrustManagers(), null);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }


        // register
        Registry<ConnectionSocketFactory> register = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", new MyConnectionSocketFactory())
            .register("https", new MySSLConnectionSocketFactory(context)).build();

        // HttpClientConnectionManager
        // FakeDnsResolver!
        cm = new PoolingHttpClientConnectionManager(register, new FakeDnsResolver());
        // PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(register);

        requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(2000)
            .setConnectTimeout(1000 * 10)
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

    public CloseableHttpClient getHttpClient() {

        // setRoutePlanner
        // HttpHost proxy = new HttpHost("localhost", 8888);
        // DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        return HttpClients
            .custom()
            .setConnectionManager(cm)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultSocketConfig(socketConfig)
            // .setKeepAliveStrategy()
            // .setConnectionManagerShared(true)
            // .setProxy(new HttpHost("45.32.21.237", 8888, "HTTP"))      // 代理
            // .setRoutePlanner(routePlanner)
            .build();
    }

    public HttpClientContext getHttpClientContext() {
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("socks.address", new InetSocketAddress(proxyHost, proxyPort));
        return context;
    }

    static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            // Return some fake DNS record for every request, we won't be using it
            return new InetAddress[]{InetAddress.getByAddress(new byte[]{1, 1, 1, 1})};
        }
    }

    static class MyConnectionSocketFactory extends PlainConnectionSocketFactory {
        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }

    static class MySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

        public MySSLConnectionSocketFactory(final SSLContext sslContext) {
            // You may need this verifier if target site's certificate is not secure
            // super(sslContext, ALLOW_ALL_HOSTNAME_VERIFIER);
            super(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }


}
